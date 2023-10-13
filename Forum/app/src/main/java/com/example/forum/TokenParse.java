package com.example.forum;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenParse {
    private String location;
    private List<Integer> priceRange;
    private int bedrooms;

    public TokenParse(String input) {
        this.location = extractLocation(input);
        this.priceRange = extractMinMaxPrice(input);
        this.bedrooms = extractBedrooms(input);
    }

    public String getLocation() {
        return location;
    }

    public List<Integer> getpriceRange() {
        return priceRange;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    private String extractLocation(String input) {

        String[] parts = input.split("\\s+");

        String FindLocation=null;
        for (String part : parts) {
            if (containsLetters(part)) {

                if (part.matches("(?i)^bel.*$")) {
                    // 如果输入是字母 bel，则补全成 "Belconnen"
                    FindLocation="city";
                } else if (part.matches("(?i)^ac.*$")) {
                    FindLocation = "Acton";
                } else if (part.matches("(?i)^ai.*$")) {
                    FindLocation = "Ainslie";
                } else if (part.matches("(?i)^am.*$")) {
                    FindLocation = "Amaroo";
                } else if (part.matches("(?i)^ar.*$")) {
                    FindLocation = "Aranda";
                } else if (part.matches("(?i)^ba.*$")) {
                    FindLocation = "Banks";
                } else if (part.matches("(?i)^bar.*$")) {
                    FindLocation = "Barton";
                } else if (part.matches("(?i)^bo.*$")) {
                    FindLocation = "Bonner";
                } else if (part.matches("(?i)^bon.*$")) {
                    FindLocation = "Bonner";
                } else if (part.matches("(?i)^bonn.*$")) {
                    FindLocation = "Bonner";
                } else if (part.matches("(?i)^bony.*$")) {
                    FindLocation = "Bonython";
                } else if (part.matches("(?i)^br.*$")) {
                    FindLocation = "Braddon";
                } else if (part.matches("(?i)^bru.*$")) {
                    FindLocation = "Bruce";
                } else if (part.matches("(?i)^cal.*$")) {
                    FindLocation = "Calwell";
                } else if (part.matches("(?i)^cam.*$")) {
                    FindLocation = "Campbell";
                } else if (part.matches("(?i)^cas.*$")) {
                    FindLocation = "Casey";
                } else if (part.matches("(?i)^cha.*$")) {
                    FindLocation = "Chapman";
                } else if (part.matches("(?i)^char.*$")) {
                    FindLocation = "Charnwood";
                } else if (part.matches("(?i)^chif.*$")) {
                    FindLocation = "Chifley";
                } else if (part.matches("(?i)^chis.*$")) {
                    FindLocation = "Chisholm";
                } else if (part.matches("(?i)^cit.*$")) {
                    FindLocation = "City";
                } else if (part.matches("(?i)^coo.*$")) {
                    FindLocation = "Cook";
                } else if (part.matches("(?i)^coo.*$")) {
                    FindLocation = "Coombs";
                } else if (part.matches("(?i)^cra.*$")) {
                    FindLocation = "Crace";
                } else if (part.matches("(?i)^cur.*$")) {
                    FindLocation = "Curtin";
                } else if (part.matches("(?i)^dea.*$")) {
                    FindLocation = "Deakin";
                } else if (part.matches("(?i)^dic.*$")) {
                    FindLocation = "Dickson";
                } else if (part.matches("(?i)^dow.*$")) {
                    FindLocation = "Downer";
                } else if (part.matches("(?i)^duf.*$")) {
                    FindLocation = "Duffy";
                } else if (part.matches("(?i)^dun.*$")) {
                    FindLocation = "Dunlop";
                } else if (part.matches("(?i)^eva.*$")) {
                    FindLocation = "Evatt";
                } else if (part.matches("(?i)^fad.*$")) {
                    FindLocation = "Fadden";
                } else if (part.matches("(?i)^far.*$")) {
                    FindLocation = "Farrer";
                } else if (part.matches("(?i)^fis.*$")) {
                    FindLocation = "Fisher";
                } else if (part.matches("(?i)^flo.*$")) {
                    FindLocation = "Florey";
                } else if (part.matches("(?i)^fly.*$")) {
                    FindLocation = "Flynn";
                } else if (part.matches("(?i)^for.*$")) {
                    FindLocation = "Forde";
                } else if (part.matches("(?i)^fors.*$")) {
                    FindLocation = "Forrest";
                } else if (part.matches("(?i)^fra.*$")) {
                    FindLocation = "Franklin";
                } else if (part.matches("(?i)^fras.*$")) {
                    FindLocation = "Fraser";
                } else if (part.matches("(?i)^fysh.*$")) {
                    FindLocation = "Fyshwick";
                } else if (part.matches("(?i)^gar.*$")) {
                    FindLocation = "Garran";
                } else if (part.matches("(?i)^gil.*$")) {
                    FindLocation = "Gilmore";
                } else if (part.matches("(?i)^gir.*$")) {
                    FindLocation = "Giralang";
                } else if (part.matches("(?i)^gor.*$")) {
                    FindLocation = "Gordon";
                } else if (part.matches("(?i)^gow.*$")) {
                    FindLocation = "Gowrie";
                } else if (part.matches("(?i)^gre.*$")) {
                    FindLocation = "Greenway";
                } else if (part.matches("(?i)^gri.*$")) {
                    FindLocation = "Griffith";
                } else if (part.matches("(?i)^gun.*$")) {
                    FindLocation = "Gungahlin";
                } else if (part.matches("(?i)^hac.*$")) {
                    FindLocation = "Hackett";
                } else if (part.matches("(?i)^har.*$")) {
                    FindLocation = "Harrison";
                } else if (part.matches("(?i)^haw.*$")) {
                    FindLocation = "Hawker";
                } else if (part.matches("(?i)^hig.*$")) {
                    FindLocation = "Higgins";
                } else if (part.matches("(?i)^hol.*$")) {
                    FindLocation = "Holder";
                } else if (part.matches("(?i)^holt.*$")) {
                    FindLocation = "Holt";
                } else if (part.matches("(?i)^hug.*$")) {
                    FindLocation = "Hughes";
                } else if (part.matches("(?i)^hum.*$")) {
                    FindLocation = "Hume";
                } else if (part.matches("(?i)^isa.*$")) {
                    FindLocation = "Isaacs";
                } else if (part.matches("(?i)^isa.*$")) {
                    FindLocation = "Isabella Plains";
                } else if (part.matches("(?i)^jac.*$")) {
                    FindLocation = "Jacka";
                } else if (part.matches("(?i)^kal.*$")) {
                    FindLocation = "Kaleen";
                } else if (part.matches("(?i)^kam.*$")) {
                    FindLocation = "Kambah";
                } else if (part.matches("(?i)^kin.*$")) {
                    FindLocation = "Kingston";
                } else if (part.matches("(?i)^lat.*$")) {
                    FindLocation = "Latham";
                } else if (part.matches("(?i)^law.*$")) {
                    FindLocation = "Lawson";
                } else if (part.matches("(?i)^lyn.*$")) {
                    FindLocation = "Lyneham";
                } else if (part.matches("(?i)^lyo.*$")) {
                    FindLocation = "Lyons";
                } else if (part.matches("(?i)^mac.*$")) {
                    FindLocation = "Macarthur";
                } else if (part.matches("(?i)^macg.*$")) {
                    FindLocation = "Macgregor";
                } else if (part.matches("(?i)^macq.*$")) {
                    FindLocation = "Macquarie";
                } else if (part.matches("(?i)^maw.*$")) {
                    FindLocation = "Mawson";
                } else if (part.matches("(?i)^mcke.*$")) {
                    FindLocation = "McKellar";
                } else if (part.matches("(?i)^mel.*$")) {
                    FindLocation = "Melba";
                } else if (part.matches("(?i)^mitc.*$")) {
                    FindLocation = "Mitchell";
                } else if (part.matches("(?i)^mon.*$")) {
                    FindLocation = "Monash";
                } else if (part.matches("(?i)^narr.*$")) {
                    FindLocation = "Narrabundah";
                } else if (part.matches("(?i)^ngu.*$")) {
                    FindLocation = "Ngunnawal";
                } else if (part.matches("(?i)^nic.*$")) {
                    FindLocation = "Nicholls";
                } else if (part.matches("(?i)^o'c.*$")) {
                    FindLocation = "O'Connor";
                } else if (part.matches("(?i)^o'm.*$")) {
                    FindLocation = "O'Malley";
                } else if (part.matches("(?i)^ox.*$")) {
                    FindLocation = "Oxley";
                } else if (part.matches("(?i)^pa.*$")) {
                    FindLocation = "Page";
                } else if (part.matches("(?i)^pal.*$")) {
                    FindLocation = "Palmerston";
                } else if (part.matches("(?i)^pea.*$")) {
                    FindLocation = "Pearce";
                } else if (part.matches("(?i)^phi.*$")) {
                    FindLocation = "Phillip";
                } else if (part.matches("(?i)^red.*$")) {
                    FindLocation = "Red Hill";
                } else if (part.matches("(?i)^rei.*$")) {
                    FindLocation = "Reid";
                } else if (part.matches("(?i)^ric.*$")) {
                    FindLocation = "Richardson";
                } else if (part.matches("(?i)^riv.*$")) {
                    FindLocation = "Rivett";
                } else if (part.matches("(?i)^scu.*$")) {
                    FindLocation = "Scullin";
                } else if (part.matches("(?i)^spe.*$")) {
                    FindLocation = "Spence";
                } else if (part.matches("(?i)^sti.*$")) {
                    FindLocation = "Stirling";
                } else if (part.matches("(?i)^swi.*$")) {
                    FindLocation = "Swinger Hill";
                } else if (part.matches("(?i)^sym.*$")) {
                    FindLocation = "Symonston";
                } else if (part.matches("(?i)^tha.*$")) {
                    FindLocation = "Tharwa";
                } else if (part.matches("(?i)^the.*$")) {
                    FindLocation = "Theodore";
                } else if (part.matches("(?i)^tor.*$")) {
                    FindLocation = "Torrens";
                } else if (part.matches("(?i)^tur.*$")) {
                    FindLocation = "Turner";
                } else if (part.matches("(?i)^wan.*$")) {
                    FindLocation = "Wanniassa";
                } else if (part.matches("(?i)^war.*$")) {
                    FindLocation = "Waramanga";
                } else if (part.matches("(?i)^wat.*$")) {
                    FindLocation = "Watson";
                } else if (part.matches("(?i)^wee.*$")) {
                    FindLocation = "Weetangera";
                } else if (part.matches("(?i)^wes.*$")) {
                    FindLocation = "Weston";
                } else if (part.matches("(?i)^west.*$")) {
                    FindLocation = "Weston Creek";
                } else if (part.matches("(?i)^wri.*$")) {
                    FindLocation = "Wright";
                } else if (part.matches("(?i)^yar.*$")) {
                    FindLocation = "Yarralumla";
                }
            }
        }

        return FindLocation ;
    }

    private boolean containsLetters(String input) {
        // 使用正则表达式检查字符串中是否包含字母
        Pattern pattern = Pattern.compile(".*[a-zA-Z].*");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }


    private List<Integer> extractMinMaxPrice(String input) {
        List<Integer> priceRange = new ArrayList<>();
        boolean validInput = false;

        String[] parts = input.split("\\s+");
        for (String part : parts) {
            if (part.contains("-")) {
                validInput = true;
                String[] rangeParts = part.split("-");
                if (rangeParts.length == 2) {
                    int firstNumber = Integer.parseInt(rangeParts[0]);
                    int secondNumber = Integer.parseInt(rangeParts[1]);
                    priceRange.add(Math.min(firstNumber, secondNumber));
                    priceRange.add(Math.max(firstNumber, secondNumber));
                }
            } else {
                try {
                    int number = Integer.parseInt(part);
                    if (number > 100) {
                        priceRange.add(number - 100);
                        priceRange.add(number + 100);
                        validInput = true;
                    }
                } catch (NumberFormatException e) {
                }
            }
        }

        if (validInput) {
            return priceRange;
        } else {
            return null;
        }
    }


    private int extractBedrooms(String input) {
        String[] parts = input.split("\\s+");

        int FindroomNumber = 0;
        for (String part : parts) {
            if (part.matches("\\d+") && Integer.parseInt(part) < 10) {
                FindroomNumber = Integer.parseInt(part);
                break;
            }
        }
        return FindroomNumber;
    }

//    public static void main(String[] args) {
//        TokenParse aa=new TokenParse("600 belssadsadss 5");
//        System.out.println(aa.getBedrooms());
//        System.out.println(aa.getLocation());
//        System.out.println(aa.getpriceRange());
//    }


}



