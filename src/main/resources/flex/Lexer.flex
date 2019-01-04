package com.ecodigital.smartcardapdupro.jflex;
import com.ecodigital.smartcardapdupro.apdu.TokenApdu;
import static com.ecodigital.smartcardapdupro.apdu.TokenApdu.*;
%%
%class Lexer
%type TokenApdu

Space = \s
L = [a-fA-F]
D = [0-9]
H = {L}|{D}
Hex = {H}{H}
SHex = {Space}{Hex}
Reset = "reset"|"RESET"
LengthUnknow = "??"


LineTerminator = \r|\n|\r\n
LineTerminator2 = \r|\n

InputCharacter = [^\r\n]
WhiteSpace = {LineTerminator} | [ \t\f]


Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
// Comment can be the last line of the file, without line terminator.
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent = ( [^*] | \*+ [^/*] )*


%{
public String lexeme;
%}
%%

{TraditionalComment} {lexeme=yytext(); return TraditionalComment;}
{EndOfLineComment} {lexeme=yytext(); return EndOfLineComment;}
{DocumentationComment} {lexeme=yytext(); return DocumentationComment;}


^{Space}*{Reset}{Space}*{LineTerminator}? {lexeme=yytext(); return RESET;}
^{Space}*{Hex}({SHex})*{Space}*{LineTerminator}? {lexeme=yytext(); return APDU;}
^{Space}*{Hex}{SHex}{SHex}{SHex}({Space}{LengthUnknow}){SHex}+ {Space}*{LineTerminator}? {lexeme=yytext(); return APDU_LC;}
{WhiteSpace} {/*Ignore*/}

. {return ERROR;}