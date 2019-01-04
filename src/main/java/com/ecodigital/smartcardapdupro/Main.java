/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ecodigital.smartcardapdupro;

import com.ecodigital.smartcardapdupro.apdu.Identificador;
import java.io.File;
import java.util.List;


/**
 *
 * @author Carlos
 */
public class Main {

    
    List<Identificador> tokenslist;
    
    
    //private static String texto = "      00 02 03              \n   /* ESTO ES UN  \nCOMENTADIO*/     \n //ASDFAS FASD   ";
    private static String texto = "      00 02 03      \r\n   1A 05  ";
    //private static String texto = "      00 02      ";
    
    /**
     * @param args the command line arguments
     */
    public static void main2(String[] args) {
        
        
        /*
        ar = new byte[]{70,54,-30,-85,-46,-65,-107,10,99,-13,54,37,-4,-74,31,22,-64,30,-111,-73,-59,-34,43,-38,-69,21,-28,37,-92,37,-5,17,6,48,-112,114,30,103,110,-32,-33,27,-64,39,35,34,126,-24,57,8,42,-88,-123,-49,47,124,-31,-11,-5,-7,72,-99,32,106,-115,-70,92,-2,78,82,-6,42,29,106,87,87,-30,-46,53,-35};
        System.out.println(HexUtils.hexify(ar, true));
        System.out.println("---------------------");
        
        ar = new byte[]{54,37,-4,-74,31,22,-64,30,-111,-73,-59,-34,43,-38,-69,21,-28,37,-92,37,-5,17,6,48,-112,114,30,103,110,-32,-33,27,-64,39,35,34,126,-24,57,8,42,-88,-123,-49,47,124,-31,-11,-5,-7,72,-99,32,106,-115,-70,92,-2,78,82,-6,42,29,106,87,87,-30,-46,53,-35,0,0,0,0,0,0,0,0,0,0};
        System.out.println(HexUtils.hexify(ar, true));
        System.out.println("---------------------");
        */
        
        /*
        ar = new byte[]{-5,-64,20,47,-109,115,-39,9,17,-94,95,0,72,-34,37,55,21,80,-65,67,-16,13,2,67,45,33,-20,33,-5,-73,56,-52,46,-78,-41,24,98,-115,-112,32,38,91,32,-109,107,60,111,-121,16,-71,-70,-71,-117,-92,120,-53,-9,105,-106,24,50,44,80,-120,38,89,55,-70,-86,-75,-123,98,-26,110,95,-127,-38,-24,-107,60};
        System.out.println(ar.length);
        System.out.println(HexUtils.hexify(ar, true));
        System.out.println("---------------------");
        */
        
        
        
        //generarLexer();
        
        //new ApduListResponseISO7816();
        

        
        
        //int a = HexUtils.getUnsignedInt(data, 0)HexStringToByte() & 0xFF;
        
        /*
        try {
            Main main = new Main();
            main.probarLexerFile();
        } catch(Exception e) {
            e.printStackTrace();
        }
        */
        
        
        
        
    }
    public static void generarLexer(){
        String path = Main.class.getResource("/flex/Lexer.flex").getPath();
        File file=new File(path);
        jflex.Main.generate(file);
        //Main.generate(file);
    }
    
    /*
    public  void probarLexerFile() throws IOException{
        int contIDs=0;
        List<String> apdus = new ArrayList<String>();
        List<String> errors = new ArrayList<String>();
        
        
        tokenslist = new LinkedList<Identificador>();
        File fichero = new File ("fichero.txt");
        PrintWriter writer;
        try {
            writer = new PrintWriter(fichero);
            writer.print(texto);
            writer.close();
        } catch (FileNotFoundException ex) {
            
        }
        Reader reader = new BufferedReader(new FileReader("fichero.txt"));
        //Lexer2 lexer = new Lexer2 (reader);
        Lexer lexer = new Lexer (reader);
        String resultado="";
        
        boolean finish = false;
        while (true) {
            Token token =lexer.yylex();
            String lexeme = lexer.yytext(); //lexer.lexeme;
            if (token == null){
                for(int i=0;i<tokenslist.size();i++){
                    System.out.println(tokenslist.get(i).nombre + "=" + tokenslist.get(i).ID);
                }
                System.out.println("RESULTADO:"+resultado);
                finish = true;
                break;
            }
            switch (token){
                case APDU:
                    resultado=resultado+ "<APDU>";
                    apdus.add(lexeme);
                    break;
                case HEX:
                    resultado=resultado+ "<HEX>";
                    break;
                case TraditionalComment:
                    resultado=resultado+ "<TraditionalComment>";
                    break;
                case EndOfLineComment:
                    resultado=resultado+ "<EndOfLineComment>";
                    break;
                case DocumentationComment:
                    resultado=resultado+ "<DocumentationComment>";
                    break;
                case ERROR:
                    resultado=resultado+ "<ERROR>";
                    errors.add(lexeme);
                    break;
                default:
                    resultado=resultado+ "<"+ lexer.lexeme + "> ";
            }
        }
        
        
        System.out.println("APDU ENCONTRADOS");
        int i = 1;
        for(String apdu : apdus){
            System.out.println((i++)+"  :"+apdu+"END");
        }
        
        
        System.out.println("ERRORES ENCONTRADOS");
        i = 1;
        for(String error : errors){
            System.out.println((i++)+"  :"+error);
        }
        
        
        
    }
*/
    
}
