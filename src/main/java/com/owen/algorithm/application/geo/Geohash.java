package com.owen.algorithm.application.geo;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Geohash {

        private static int numbits = 6 * 5;
        final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                        '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p',
                        'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
       
        final static HashMap<Character, Integer> lookup = new HashMap<Character, Integer>();
        private static final Map<Direction, Map<Parity, String>> BORDERS = createBorders();
        private static final Map<Direction, Map<Parity, String>> NEIGHBOURS = createNeighbours();
        public enum Parity {
            EVEN, ODD;
        }
        public enum Direction {
            BOTTOM, TOP, LEFT, RIGHT;

            /**
             * Returns the opposite direction. For example LEFT.opposite() == RIGHT.
             * 
             * @return the opposite direction
             */
            public Direction opposite() {
                if (this == BOTTOM)
                    return TOP;
                else if (this == TOP)
                    return BOTTOM;
                else if (this == LEFT)
                    return RIGHT;
                else
                    return LEFT;
            }
        }
        
        static {
                int i = 0;
                for (char c : digits)
                        lookup.put(c, i++);
        }

        public double[] decode(String geohash) {
                StringBuilder buffer = new StringBuilder();
                for (char c : geohash.toCharArray()) {

                        int i = lookup.get(c) + 32;
                        buffer.append( Integer.toString(i, 2).substring(1) );
                }
               
                BitSet lonset = new BitSet();
                BitSet latset = new BitSet();
               
                //even bits
                int j =0;
                for (int i=0; i< numbits*2;i+=2) {
                        boolean isSet = false;
                        if ( i < buffer.length() )
                          isSet = buffer.charAt(i) == '1';
                        lonset.set(j++, isSet);
                }
               
                //odd bits
                j=0;
                for (int i=1; i< numbits*2;i+=2) {
                        boolean isSet = false;
                        if ( i < buffer.length() )
                          isSet = buffer.charAt(i) == '1';
                        latset.set(j++, isSet);
                }
               //中国地理坐标：东经73°至东经135°，北纬4°至北纬53°
              //  double lon = decode(lonset, 70, 140);
               // double lat = decode(latset, 0, 60);
                 double lon = decode(lonset, -180, 180);
                 double lat = decode(latset, -90, 90); 
                return new double[] {lat, lon};        
        }
       
        private double decode(BitSet bs, double floor, double ceiling) {
                double mid = 0;
                for (int i=0; i<bs.length(); i++) {
                        mid = (floor + ceiling) / 2;
                        if (bs.get(i))
                                floor = mid;
                        else
                                ceiling = mid;
                }
                return mid;
        }
       
       
        public String encode(double lat, double lon) {
                BitSet latbits = getBits(lat, -90, 90);
                BitSet lonbits = getBits(lon, -180, 180);
                StringBuilder buffer = new StringBuilder();
                for (int i = 0; i < numbits; i++) {
                        buffer.append( (lonbits.get(i))?'1':'0');
                        buffer.append( (latbits.get(i))?'1':'0');
                }
                //System.out.println("lon+lat: "+buffer+", radix 10, "+Long.parseLong(buffer.toString(),2));
                return base32(Long.parseLong(buffer.toString(), 2));
        }

        private BitSet getBits(double lat, double floor, double ceiling) {
                BitSet buffer = new BitSet(numbits);
                for (int i = 0; i < numbits; i++) {
                        double mid = (floor + ceiling) / 2;
                        if (lat >= mid) {
                                buffer.set(i);
                                floor = mid;
                        } else {
                                ceiling = mid;
                        }
                }
               // System.out.println("bits: "+buffer.toString()+", cardinality: "+buffer.cardinality()+", size: "+buffer.size()+", numbits: "+numbits);

                return buffer;
        }

        public static String base32(long i) {
                char[] buf = new char[65];
                int charPos = 64;
                boolean negative = (i < 0);
                if (!negative)
                        i = -i;
                while (i <= -32) {
                        buf[charPos--] = digits[(int) (-(i % 32))];
                        i /= 32;
                }
                buf[charPos] = digits[(int) (-i)];

                if (negative)
                        buf[--charPos] = '-';
               // System.out.println("buf, "+buf.toString()+", start: "+charPos+", length: "+(65-charPos));
                return new String(buf, charPos, (65 - charPos));
        }
        

        /**
         * Returns a map to be used in adjacent hash calculations.
         * 
         * @return map
         */
        private static Map<Direction, Map<Parity, String>> createNeighbours() {
            Map<Direction, Map<Parity, String>> m = createDirectionParityMap();
            m.get(Direction.RIGHT).put(Parity.EVEN, "bc01fg45238967deuvhjyznpkmstqrwx");
            m.get(Direction.LEFT).put(Parity.EVEN, "238967debc01fg45kmstqrwxuvhjyznp");
            m.get(Direction.TOP).put(Parity.EVEN, "p0r21436x8zb9dcf5h7kjnmqesgutwvy");
            m.get(Direction.BOTTOM).put(Parity.EVEN, "14365h7k9dcfesgujnmqp0r2twvyx8zb");
            addOddParityEntries(m);
            return m;
        }
        
        /**
         * Returns a list of the 8 surrounding hashes for a given hash in order
         * left,right,top,bottom,left-top,left-bottom,right-top,right-bottom.
         * 
         * @param hash
         *            source
         * @return a list of neighbour hashes
         */
        public static List<String> getNeighbours(String hash) {
            List<String> list = new ArrayList<String>();
            String left = adjacentHash(hash, Direction.LEFT);
            String right = adjacentHash(hash, Direction.RIGHT);
            list.add(left);
            list.add(right);
            list.add(adjacentHash(hash, Direction.TOP));
            list.add(adjacentHash(hash, Direction.BOTTOM));
            list.add(adjacentHash(left, Direction.TOP));
            list.add(adjacentHash(left, Direction.BOTTOM));
            list.add(adjacentHash(right, Direction.TOP));
            list.add(adjacentHash(right, Direction.BOTTOM));
            return list;
        }
        
        public static String adjacentHash(String hash, Direction direction) {

/*            String adjacentHashAtBorder = adjacentHashAtBorder(hash, direction);
            if (adjacentHashAtBorder != null)
                return adjacentHashAtBorder;*/
            String source = hash.toLowerCase();
            char lastChar = source.charAt(source.length() - 1);
            Parity parity = (source.length() % 2 == 0) ? Parity.EVEN : Parity.ODD;
            String base = source.substring(0, source.length() - 1);
            if (BORDERS.get(direction).get(parity).indexOf(lastChar) != -1)
                base = adjacentHash(base, direction);
            return base + digits[NEIGHBOURS.get(direction).get(parity).indexOf(lastChar)];
        }

        private static Map<Direction, Map<Parity, String>> createBorders() {
            Map<Direction, Map<Parity, String>> m = createDirectionParityMap();
            m.get(Direction.RIGHT).put(Parity.EVEN, "bcfguvyz");
            m.get(Direction.LEFT).put(Parity.EVEN, "0145hjnp");
            m.get(Direction.TOP).put(Parity.EVEN, "prxz");
            m.get(Direction.BOTTOM).put(Parity.EVEN, "028b");
            addOddParityEntries(m);
            return m;
        }
        
        /**
         * Create a direction and parity map for use in adjacent hash calculations.
         * 
         * @return map
         */
        private static Map<Direction, Map<Parity, String>> createDirectionParityMap() {
            Map<Direction, Map<Parity, String>> m = new HashMap<Direction, Map<Parity, String>>();
            m.put(Direction.BOTTOM, Geohash.<Parity, String> newHashMap());
            m.put(Direction.TOP, Geohash.<Parity, String> newHashMap());
            m.put(Direction.LEFT, Geohash.<Parity, String> newHashMap());
            m.put(Direction.RIGHT, Geohash.<Parity, String> newHashMap());
            return m;
        }
        
        private static <T, D> Map<T, D> newHashMap() {
            return new HashMap<T, D>();
        }
        /**
         * Puts odd parity entries in the map m based purely on the even entries.
         * 
         * @param m
         *            map
         */
        private static void addOddParityEntries(Map<Direction, Map<Parity, String>> m) {
            m.get(Direction.BOTTOM).put(Parity.ODD, m.get(Direction.LEFT).get(Parity.EVEN));
            m.get(Direction.TOP).put(Parity.ODD, m.get(Direction.RIGHT).get(Parity.EVEN));
            m.get(Direction.LEFT).put(Parity.ODD, m.get(Direction.BOTTOM).get(Parity.EVEN));
            m.get(Direction.RIGHT).put(Parity.ODD, m.get(Direction.TOP).get(Parity.EVEN));
        }
        
        
/*        private static String adjacentHashAtBorder(String hash, Direction direction) {
            // check if hash is on edge and direction would push us over the edge
            // if so, wrap round to the other limit for longitude
            // or if at latitude boundary (a pole) then spin longitude around 180
            // degrees.
            LatLong centre = decodeHash(hash);

            // if rightmost hash
            if (Direction.RIGHT.equals(direction)) {
                if (Math.abs(centre.getLon() + widthDegrees(hash.length()) / 2 - 180) < PRECISION) {
                    return encodeHash(centre.getLat(), -180, hash.length());
                }
            }
            // if leftmost hash
            else if (Direction.LEFT.equals(direction)) {
                if (Math.abs(centre.getLon() - widthDegrees(hash.length()) / 2 + 180) < PRECISION) {
                    return encodeHash(centre.getLat(), 180, hash.length());
                }
            }
            // if topmost hash
            else if (Direction.TOP.equals(direction)) {
                if (Math.abs(centre.getLat() + widthDegrees(hash.length()) / 2 - 90) < PRECISION) {
                    return encodeHash(centre.getLat(), centre.getLon() + 180, hash.length());
                }
            }
            // if bottommost hash
            else {
                if (Math.abs(centre.getLat() - widthDegrees(hash.length()) / 2 + 90) < PRECISION) {
                    return encodeHash(centre.getLat(), centre.getLon() + 180, hash.length());
                }
            }

            return null;
        }*/

        
        public static void main(String[] args){
        	double lon;
        	double lat;
        	lat=39.92324;
        	lon=116.3906;      
        	//lat=40.92324;
        	
        	//lon=117.3906;   
        	Geohash gh=new Geohash();
        	String code=gh.encode(lat, lon);
        	String hash=code.substring(0,4);
        	System.out.println("geohash: "+code);
        	double[] pos=gh.decode(code);
        	System.out.println("lat: "+pos[0]+", lon: "+pos[1]);
        	List<String> neighbours=gh.getNeighbours(hash);
        	for(String neighbour:neighbours){
        		System.out.println(neighbour);
        	}
        }

}