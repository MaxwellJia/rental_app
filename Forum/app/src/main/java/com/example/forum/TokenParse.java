package com.example.forum;

import java.util.ArrayList;
import java.util.List;

public class TokenParse {


    //    ] suburbs = {
////                "Acton", "Ainslie", "Amaroo", "Aranda", "Banks", "Barton", "Belconnen", "Bonner", "Bonython", "Braddon",
////                "Bruce", "Calwell", "Campbell", "Casey", "Chapman", "Charnwood", "Chifley", "Chisholm", "City", "Cook",
////                "Coombs", "Crace", "Curtin", "Deakin", "Dickson", "Downer", "Duffy", "Dunlop", "Evatt", "Fadden",
////                "Farrer", "Fisher", "Florey", "Flynn", "Forde", "Forrest", "Franklin", "Fraser", "Fyshwick", "Garran",
////                "Gilmore", "Giralang", "Gordon", "Gowrie", "Greenway", "Griffith", "Gungahlin", "Hackett", "Harrison",
////                "Hawker", "Higgins", "Holder", "Holt", "Hughes", "Hume", "Isaacs", "Isabella Plains", "Jacka", "Kaleen",
////                "Kambah", "Kingston", "Latham", "Lawson", "Lyneham", "Lyons", "Macarthur", "Macgregor", "Macquarie", "Mawson",
////                "McKellar", "Melba", "Mitchell", "Monash", "Narrabundah", "Ngunnawal", "Nicholls", "O'Connor", "O'Malley",
////                "Oxley", "Page", "Palmerston", "Pearce", "Phillip", "Red Hill", "Reid", "Richardson", "Rivett", "Scullin",
////                "Spence", "Stirling", "Swinger Hill", "Symonston", "Tharwa", "Theodore", "Torrens", "Turner", "Wanniassa",
////                "Waramanga", "Watson", "Weetangera", "Weston", "Weston Creek", "Wright", "Yarralumla"
////        };
    public static List<String> tokenize(String input) {
        List<String> tokens = new ArrayList<>();

        // 检查第一个部分是否匹配地名
        if (input.matches("(?i)^bel.*$")) {
            // 如果输入是字母 bel，则补全成 "Belconnen"
            tokens.add("Belconnen");
        } // 根据输入的内容进行分割
        else if (input.matches("(?i)^ac.*$")) {
            // 如果输入是 1 到 3 位数字，则补全成 "$" 后加上数字
            tokens.add("Acton");
        }else if (input.matches("(?i)^ai.*$")) {
            // 如果输入是 1 到 3 位数字，则补全成 "$" 后加上数字
            tokens.add("Ainslie");
        } else if (input.matches("(?i)^am.*$")) {
            // 如果输入是 1 到 3 位数字，则补全成 "$" 后加上数字
            tokens.add("Amaroo");
        }else if (input.matches("(?i)^ar.*$")) {
            // 如果输入是 1 到 3 位数字，则补全成 "$" 后加上数字
            tokens.add("Aranda");
        }else if (input.matches("(?i)^ba.*$")) {
            // 如果输入是 1 到 3 位数字，则补全成 "$" 后加上数字
            tokens.add("Banks");
        }else if (input.matches("(?i)^bar.*$")) {
            tokens.add("Barton");
        } else if (input.matches("(?i)^bo.*$")) {
            tokens.add("Bonner");
        } else if (input.matches("(?i)^bon.*$")) {
            tokens.add("Bonner");
        } else if (input.matches("(?i)^bonn.*$")) {
            tokens.add("Bonner");
        } else if (input.matches("(?i)^bony.*$")) {
            tokens.add("Bonython");
        } else if (input.matches("(?i)^br.*$")) {
            tokens.add("Braddon");
        } else if (input.matches("(?i)^bru.*$")) {
            tokens.add("Bruce");
        } else if (input.matches("(?i)^cal.*$")) {
            tokens.add("Calwell");
        } else if (input.matches("(?i)^cam.*$")) {
            tokens.add("Campbell");
        } else if (input.matches("(?i)^cas.*$")) {
            tokens.add("Casey");
        } else if (input.matches("(?i)^cha.*$")) {
            tokens.add("Chapman");
        } else if (input.matches("(?i)^char.*$")) {
            tokens.add("Charnwood");
        } else if (input.matches("(?i)^chif.*$")) {
            tokens.add("Chifley");
        } else if (input.matches("(?i)^chis.*$")) {
            tokens.add("Chisholm");
        } else if (input.matches("(?i)^cit.*$")) {
            tokens.add("City");
        } else if (input.matches("(?i)^coo.*$")) {
            tokens.add("Cook");
        } else if (input.matches("(?i)^coo.*$")) {
            tokens.add("Coombs");
        } else if (input.matches("(?i)^cra.*$")) {
            tokens.add("Crace");
        } else if (input.matches("(?i)^cur.*$")) {
            tokens.add("Curtin");
        } else if (input.matches("(?i)^dea.*$")) {
            tokens.add("Deakin");
        } else if (input.matches("(?i)^dic.*$")) {
            tokens.add("Dickson");
        } else if (input.matches("(?i)^dow.*$")) {
            tokens.add("Downer");
        } else if (input.matches("(?i)^duf.*$")) {
            tokens.add("Duffy");
        } else if (input.matches("(?i)^dun.*$")) {
            tokens.add("Dunlop");
        } else if (input.matches("(?i)^eva.*$")) {
            tokens.add("Evatt");
        } else if (input.matches("(?i)^fad.*$")) {
            tokens.add("Fadden");
        } else if (input.matches("(?i)^far.*$")) {
            tokens.add("Farrer");
        } else if (input.matches("(?i)^fis.*$")) {
            tokens.add("Fisher");
        } else if (input.matches("(?i)^flo.*$")) {
            tokens.add("Florey");
        } else if (input.matches("(?i)^fly.*$")) {
            tokens.add("Flynn");
        } else if (input.matches("(?i)^for.*$")) {
            tokens.add("Forde");
        } else if (input.matches("(?i)^fors.*$")) {
            tokens.add("Forrest");
        } else if (input.matches("(?i)^fra.*$")) {
            tokens.add("Franklin");
        } else if (input.matches("(?i)^fras.*$")) {
            tokens.add("Fraser");
        } else if (input.matches("(?i)^fysh.*$")) {
            tokens.add("Fyshwick");
        } else if (input.matches("(?i)^gar.*$")) {
            tokens.add("Garran");
        } else if (input.matches("(?i)^gil.*$")) {
            tokens.add("Gilmore");
        } else if (input.matches("(?i)^gir.*$")) {
            tokens.add("Giralang");
        } else if (input.matches("(?i)^gor.*$")) {
            tokens.add("Gordon");
        } else if (input.matches("(?i)^gow.*$")) {
            tokens.add("Gowrie");
        } else if (input.matches("(?i)^gre.*$")) {
            tokens.add("Greenway");
        } else if (input.matches("(?i)^gri.*$")) {
            tokens.add("Griffith");
        } else if (input.matches("(?i)^gun.*$")) {
            tokens.add("Gungahlin");
        } else if (input.matches("(?i)^hac.*$")) {
            tokens.add("Hackett");
        } else if (input.matches("(?i)^har.*$")) {
            tokens.add("Harrison");
        } else if (input.matches("(?i)^haw.*$")) {
            tokens.add("Hawker");
        } else if (input.matches("(?i)^hig.*$")) {
            tokens.add("Higgins");
        } else if (input.matches("(?i)^hol.*$")) {
            tokens.add("Holder");
        } else if (input.matches("(?i)^holt.*$")) {
            tokens.add("Holt");
        } else if (input.matches("(?i)^hug.*$")) {
            tokens.add("Hughes");
        } else if (input.matches("(?i)^hum.*$")) {
            tokens.add("Hume");
        } else if (input.matches("(?i)^isa.*$")) {
            tokens.add("Isaacs");
        } else if (input.matches("(?i)^isa.*$")) {
            tokens.add("Isabella Plains");
        } else if (input.matches("(?i)^jac.*$")) {
            tokens.add("Jacka");
        } else if (input.matches("(?i)^kal.*$")) {
            tokens.add("Kaleen");
        } else if (input.matches("(?i)^kam.*$")) {
            tokens.add("Kambah");
        } else if (input.matches("(?i)^kin.*$")) {
            tokens.add("Kingston");
        } else if (input.matches("(?i)^lat.*$")) {
            tokens.add("Latham");
        } else if (input.matches("(?i)^law.*$")) {
            tokens.add("Lawson");
        } else if (input.matches("(?i)^lyn.*$")) {
            tokens.add("Lyneham");
        } else if (input.matches("(?i)^lyo.*$")) {
            tokens.add("Lyons");
        } else if (input.matches("(?i)^mac.*$")) {
            tokens.add("Macarthur");
        } else if (input.matches("(?i)^macg.*$")) {
            tokens.add("Macgregor");
        } else if (input.matches("(?i)^macq.*$")) {
            tokens.add("Macquarie");
        } else if (input.matches("(?i)^maw.*$")) {
            tokens.add("Mawson");
        } else if (input.matches("(?i)^mcke.*$")) {
            tokens.add("McKellar");
        } else if (input.matches("(?i)^mel.*$")) {
            tokens.add("Melba");
        } else if (input.matches("(?i)^mitc.*$")) {
            tokens.add("Mitchell");
        } else if (input.matches("(?i)^mon.*$")) {
            tokens.add("Monash");
        } else if (input.matches("(?i)^narr.*$")) {
            tokens.add("Narrabundah");
        } else if (input.matches("(?i)^ngu.*$")) {
            tokens.add("Ngunnawal");
        } else if (input.matches("(?i)^nic.*$")) {
            tokens.add("Nicholls");
        } else if (input.matches("(?i)^o'c.*$")) {
            tokens.add("O'Connor");
        } else if (input.matches("(?i)^o'm.*$")) {
            tokens.add("O'Malley");
        } else if (input.matches("(?i)^ox.*$")) {
            tokens.add("Oxley");
        } else if (input.matches("(?i)^pa.*$")) {
            tokens.add("Page");
        } else if (input.matches("(?i)^pal.*$")) {
            tokens.add("Palmerston");
        } else if (input.matches("(?i)^pea.*$")) {
            tokens.add("Pearce");
        } else if (input.matches("(?i)^phi.*$")) {
            tokens.add("Phillip");
        } else if (input.matches("(?i)^red.*$")) {
            tokens.add("Red Hill");
        } else if (input.matches("(?i)^rei.*$")) {
            tokens.add("Reid");
        } else if (input.matches("(?i)^ric.*$")) {
            tokens.add("Richardson");
        } else if (input.matches("(?i)^riv.*$")) {
            tokens.add("Rivett");
        } else if (input.matches("(?i)^scu.*$")) {
            tokens.add("Scullin");
        } else if (input.matches("(?i)^spe.*$")) {
            tokens.add("Spence");
        } else if (input.matches("(?i)^sti.*$")) {
            tokens.add("Stirling");
        } else if (input.matches("(?i)^swi.*$")) {
            tokens.add("Swinger Hill");
        } else if (input.matches("(?i)^sym.*$")) {
            tokens.add("Symonston");
        } else if (input.matches("(?i)^tha.*$")) {
            tokens.add("Tharwa");
        } else if (input.matches("(?i)^the.*$")) {
            tokens.add("Theodore");
        } else if (input.matches("(?i)^tor.*$")) {
            tokens.add("Torrens");
        } else if (input.matches("(?i)^tur.*$")) {
            tokens.add("Turner");
        } else if (input.matches("(?i)^wan.*$")) {
            tokens.add("Wanniassa");
        } else if (input.matches("(?i)^war.*$")) {
            tokens.add("Waramanga");
        } else if (input.matches("(?i)^wat.*$")) {
            tokens.add("Watson");
        } else if (input.matches("(?i)^wee.*$")) {
            tokens.add("Weetangera");
        } else if (input.matches("(?i)^wes.*$")) {
            tokens.add("Weston");
        } else if (input.matches("(?i)^west.*$")) {
            tokens.add("Weston Creek");
        } else if (input.matches("(?i)^wri.*$")) {
            tokens.add("Wright");
        } else if (input.matches("(?i)^yar.*$")) {
            tokens.add("Yarralumla");
        }



        return tokens;
    }

    public static String parse(List<String> tokens) {
        // 将令牌拼接成最终输出
        return String.join(" ", tokens);
    }
}



