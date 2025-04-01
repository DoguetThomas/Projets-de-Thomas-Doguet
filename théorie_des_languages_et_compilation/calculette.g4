// Déclaration de la grammaire pour le calcul

grammar Calculette;

@parser::members {
    
    // On crée un objet pour gérer les symboles (les variables et leur adresse en mémoire)
    private TablesSymboles tablesSymboles = new TablesSymboles();

    // Voici les fonctions qui génèrent les instructions d'assemblage pour différentes opérations

    private String printPUSHI(String entier) { 
        return "PUSHI " + entier + "\n";  // Pour envoyer un entier sur la pile
    }

    private String printPUSHF(String flottant) { 
        return "PUSHF " + flottant + "\n";  // Pour envoyer un nombre à virgule flottante sur la pile
    }

    private String printStoreG(int varAddress) {
        return "STOREG " + varAddress + "\n";  // Pour sauvegarder une valeur à une adresse mémoire
    }

    private String printSTOREL(int varAddress) {
        return "STOREL " + varAddress + "\n";  // Pour sauvegarder une valeur à une adresse mémoire
    }

    private String printPushG(int varAddress){
        return "PUSHG " + varAddress + "\n";  // Pour charger une valeur depuis une adresse mémoire
    }

    private String printPUSHL(int varAddress){
        return "PUSHL " + varAddress + "\n";  // Pour charger une valeur depuis une adresse mémoire
    }

    private String printWRITEPOP() {
        return "WRITE" + "\nPOP\n";  // Pour afficher une valeur et ensuite la retirer de la pile
    }

    private String printPOP() {
        return "POP\n";  // Pour afficher une valeur et ensuite la retirer de la pile
    }
    private String printWRITEFPOPPOP(){
        return "WRITEF" + "\nPOP\nPOP\n";
    }
    private String printREAD(){
        return "READ\n";  // Pour lire une valeur (par exemple depuis l'entrée utilisateur)
    }

    private String printREADF(){
        return "READF\n";
    }

    private String printADD(){
        return "ADD\n";  // Pour additionner deux valeurs
    }

    private String printFADD(){
        return "FADD\n";
    }

    private String printJump(String label){
        return "JUMP " + label + "\n";//Pour bouger d'un endroit vers un autre 
    }

    private String printJumpF(String label){
        return "JUMPF " + label + "\n";//Pour bouger d'un endroit vers un autre en fonction d'une condition
    }

    private String printCALL(String label){
        return "CALL " + label + "\n";
    }
    //création des affichages pour condtions
    private String printMUL(){return "MUL\n";}
    private String printEQUAL(){return "EQUAL\n";}
    private String printINF(){return "INF\n";}
    private String printSUP(){return "SUP\n";}
    private String printSUPEQ(){return "SUPEQ\n";}
    private String printINFEQ(){return "INFEQ\n";}
    private String printNEQ(){return "NEQ\n";}
   
    private String printFMUL(){return "FMUL\n";}
    private String printFEQUAL(){return "FEQUAL\n";}
    private String printFINF(){return "FINF\n";}
    private String printFSUP(){return "FSUP\n";}
    private String printFSUPEQ(){return "FSUPEQ\n";}
    private String printFINFEQ(){return "FINFEQ\n";}
    private String printFNEQ(){return "FNEQ\n";}

    private int _cur_label = 1;
    /** générateur de nom d'étiquettes pour les boucles */
    private String newLabel( ) { return "Label"+(_cur_label++); }; 
    // Cette fonction génère des instructions pour les opérations arithmétiques
    private String printLabel(String name){
        return "LABEL " + name + "\n";
    }
    private String printExpr(String x, String op, String y, String type) { 
        // Selon l'opérateur, on génère l'instruction correspondante
        if (type.equals("int")){
            if (op.equals("*")) {
                return x + y + "MUL\n";  // Multiplication
            } else if (op.equals("/")) {
                return x + y + "DIV\n";  // Division
            } else if (op.equals("%")) {
                return x + y + "MOD\n";  // Modulo
            } else if (op.equals("+")) {
                return x + y + "ADD\n";  // Addition
            } else if (op.equals("-")) {
                return x + y + "SUB\n";  // Soustraction
            } else {
                // Si l'opérateur n'est pas reconnu, on affiche une erreur
                System.err.println("Opérateur arithmétique incorrect : '" + op + "'");
                throw new IllegalArgumentException("Opérateur arithmétique incorrect : '" + op + "'");
            }
        }
        else{
            if (op.equals("*")) {
                return x + y + "FMUL\n";  // Multiplication
            } else if (op.equals("/")) {
                return x + y + "FDIV\n";  // Division
            } else if (op.equals("+")) {
                return x + y + "FADD\n";  // Addition
            } else if (op.equals("-")) {
                return x + y + "FSUB\n";  // Soustraction
            } else {
                // Si l'opérateur n'est pas reconnu, on affiche une erreur
                System.err.println("Opérateur arithmétique incorrect : '" + op + "'");
                throw new IllegalArgumentException("Opérateur arithmétique incorrect : '" + op + "'");
            }
        }
        
    }
}

start : calcul EOF ;  // La règle de départ : un calcul suivi de la fin du fichier

calcul returns [ String code ] 
@init{ $code = new String(); }   // On initialise une variable pour stocker le code généré
@after{ System.out.println($code); } // Affichage du code généré à la fin du calcul

    :   (decl { $code += $decl.code; })*  // Déclarations de variables
        { $code += "  JUMP Start\n"; }
        NEWLINE*  // Ignore les nouvelles lignes
        (fonction { $code += $fonction.code; })*
        NEWLINE*
        { $code += "LABEL Start\n"; }
        (instruction { $code += $instruction.code; })*  // On exécute toutes les instructions de calcul ou autres

        { $code += "  HALT\n"; }  // On termine le programme avec l'instruction HALT
    ;

bloc returns [ String code ]  
@init { $code = new String(); }  // Initialisation du code pour un bloc
    : '{' 
        (instruction { $code += $instruction.code; })*  // Ajout de toutes les instructions à l'intérieur du bloc
      '}'  
      NEWLINE*  // On ignore les nouvelles lignes après un bloc
    | 'else' instruction
      { $code = $instruction.code; } 
    | 'then' instruction
      { $code = $instruction.code; } // Ajoute une seule instruction sans accolade
    ;

instruction returns [ String code ]  // Définition des différentes instructions possibles
    : expr finInstruction  // Une expression suivie de la fin d'instruction
        { $code = $expr.chaine; }
    | assignation finInstruction  // Une assignation suivie de la fin d'instruction
        { 
            $code = $assignation.code;
        }
    | input finInstruction  // Une instruction d'entrée (lecture) suivie de la fin d'instruction
        { 
            $code = $input.code;
        }
    | output finInstruction  // Une instruction de sortie (affichage) suivie de la fin d'instruction
        { 
            $code = $output.code;
        }

    | RETURN expr finInstruction
        {
            VariableInfo returnVar = tablesSymboles.getReturn();
            String type = returnVar.type;
            $code = $expr.chaine;
            int adresse = returnVar.address;
            if (type.equals("int")){
                $code += printSTOREL(adresse);
            }
            else{
                $code += printSTOREL(adresse+1);
                $code += printSTOREL(adresse);
            }
            $code += "RETURN\n";
        }
    | boucle   // Ajouter l'appel à la règle boucle
        { 
            $code = $boucle.code; 
        }
    | bloc 
    {
            $code= $bloc.code;
    }
    | finInstruction  // Une instruction vide (juste la fin de l'instruction)
        { $code = ""; }
    ;



expr returns [ String chaine,String type ]  // Une expression arithmétique
   : '-' a=expr {  
        $type=$a.type;
        if ($type.equals("int")){
            $chaine = printPUSHI("-"+ $a.text);
        }
        else{
            $chaine = printPUSHF("-"+ $a.text);
        }
            }  // Gère les nombres négatifs
   | a=expr op=('*' | '/' | '%') b=expr { $chaine = printExpr($a.chaine, $op.text, $b.chaine, $a.type); $type = $a.type;}  // Opérations de multiplication, division ou modulo
   | a=expr op=('+' | '-') b=expr { $chaine = printExpr($a.chaine, $op.text, $b.chaine, $a.type); $type = $a.type;}  // Opérations d'addition ou de soustraction
   | '(' a=expr ')' { $chaine = $a.chaine; $type=$a.type;}  // Parenthèses pour indiquer la priorité 
   | ENTIER { $chaine = printPUSHI($ENTIER.text); $type="int";}  // Si c'est un entier, on l'envoie sur la pile
   | FLOTTANT { $chaine = printPUSHF($FLOTTANT.text); $type="double"; }
   | IDENTIFIANT {  // Si c'est un identifiant (une variable), on charge sa valeur
            VariableInfo vi = tablesSymboles.getVar($IDENTIFIANT.text);
            int varAddress = vi.address;
            $type = vi.type;
            if($type.equals("int")){
                if (vi.scope == VariableInfo.Scope.GLOBAL){
                $chaine = printPushG(varAddress);
                }
                else if (vi.scope == VariableInfo.Scope.LOCAL){
                    $chaine = printPUSHL(varAddress);
                }
                else if (vi.scope == VariableInfo.Scope.PARAM){
                    $chaine = printPUSHL(varAddress);
                }
            }
            else{
                if (vi.scope == VariableInfo.Scope.GLOBAL){
                    $chaine = printPushG(varAddress);
                    $chaine += printPushG(varAddress+1);
                }
                else if (vi.scope == VariableInfo.Scope.LOCAL){
                    $chaine = printPUSHL(varAddress);
                    $chaine += printPUSHL(varAddress+1);
                }
                else if (vi.scope == VariableInfo.Scope.PARAM){
                    $chaine = printPUSHL(varAddress);
                    $chaine += printPUSHL(varAddress+1);
                }
            }
            
            
        }  // Charge la variable depuis sa mémoire
    | IDENTIFIANT '(' args ')'                  // Appel de fonction
    {

        String identifiant = $IDENTIFIANT.text;

        String type = tablesSymboles.getFunction(identifiant);
        $type = type;
        if (type.equals("int")){
            $chaine = printPUSHI("0");
        }
        else{
            $chaine = printPUSHF("0.0");
        }
        

        $chaine += $args.code;
        $chaine += printCALL(identifiant);

        for(int i = 0; i < $args.size ; i++){
            $chaine += printPOP();
        }

    }
    |'('TYPE')' expr
    {   
        String destType = $TYPE.text;
        String oriType = $expr.type;

        if(!oriType.equals(destType)){
            $chaine = $expr.chaine;
            if (destType.equals("int")){
                $chaine += "FTOI\n";
            }
            else if (destType.equals("double")){
                $chaine += "ITOF\n";
            }
            else if (destType.equals("bool")){
                if (oriType.equals("int")){
                    $chaine += printPUSHI("0");
                    $chaine += printNEQ();
                }
                else if (oriType.equals("float")){
                    $chaine += printPUSHF("0.0");
                    $chaine += printFNEQ();
                }
            }
        }
    }
   ;

finInstruction : ( NEWLINE | ';' )+ ;  // La fin d'une instruction peut être une nouvelle ligne ou un point-virgule

decl returns [ String code ]  // Déclaration d'une variable
    : TYPE IDENTIFIANT finInstruction
        {
            String type = $TYPE.text;
            String identifiant = $IDENTIFIANT.text;
            tablesSymboles.addVarDecl(identifiant, type);  // On ajoute la déclaration de la variable
            VariableInfo vi = tablesSymboles.getVar(identifiant);
            int varAddress = vi.address;  // On récupère l'adresse mémoire de la variable
            if (type.equals("int")) {  // Si c'est un entier
                $code = printPUSHI("0");  // On initialise la variable à 0
            }
            else if (type.equals("double")) {  // Si c'est un entier
                $code = printPUSHF("0.0");  // On initialise la variable à 0
            } 
            else if (type.equals("bool")){
                $code = printPUSHI("0");
            }
        }
    | TYPE IDENTIFIANT '=' expr finInstruction  // Déclaration avec initialisation
        {
            String type = $TYPE.text;
            String exprCode = $expr.chaine;
            String identifiant = $IDENTIFIANT.text;
            tablesSymboles.addVarDecl(identifiant, type);  // On ajoute la déclaration de la variable

            VariableInfo vi = tablesSymboles.getVar(identifiant);
            int varAddress = vi.address;  // On récupère l'adresse mémoire de la variable

            if (type.equals("int")) {  // Si c'est un entier
                $code = printPUSHI("0");  // Initialisation à 0
                $code += exprCode;  // On ajoute l'expression de l'initialisation
                $code += printADD();  // On effectue l'addition si besoin
            }
            else if (type.equals("double")) {
                $code = printPUSHF("0.0");  // Initialisation à 0
                $code += exprCode;  // On ajoute l'expression de l'initialisation
                $code += printFADD();
            }
            else if (type.equals("bool")) {
                $code = printPUSHI("0");  // Initialisation à 0
                $code += exprCode;  // On ajoute l'expression de l'initialisation
                $code += printFADD();
            }
        }
    ;
//règle pour les condition
condition returns [String code]
    //gestion des opérateurs de comparaison
     :BOOL { 
        if ($BOOL.text.equals("True")){
            $code = printPUSHI("1");
        }
        else{
            $code = printPUSHI("0");
        }
    }
    | a=expr '==' b=expr 
    { 
        if($a.type.equals("int")){
            $code = $a.chaine;
            $code += $b.chaine;
            $code += printEQUAL();
        }
        else{
            $code = $a.chaine;
            $code += $b.chaine;
            $code += printFEQUAL();
        }
        
        }
    | a=expr '!=' b=expr 
    {
        if($a.type.equals("int")){
            $code = $a.chaine;
            $code += $b.chaine;
            $code += printNEQ();
        }
        else{
            $code = $a.chaine;
            $code += $b.chaine;
            $code += printFNEQ();
        }
    }
    | a=expr '<>' b=expr 
    {
        if($a.type.equals("int")){
            $code = $a.chaine;
            $code += $b.chaine;
            $code += printNEQ();
        }
        else{
            $code = $a.chaine;
            $code += $b.chaine;
            $code += printFNEQ();
        }
    }
    | a=expr '<' b=expr 
    {
        if($a.type.equals("int")){
            $code = $a.chaine;
            $code += $b.chaine;
            $code += printINF();
        }
        else{
            $code = $a.chaine;
            $code += $b.chaine;
            $code += printFINF();
        }
    }
    | a=expr '>' b=expr 
    {   
        if($a.type.equals("int")){
            $code = $a.chaine;
            $code += $b.chaine;
            $code += printSUP();
        }
        else{
            $code = $a.chaine;
            $code += $b.chaine;
            $code += printFSUP();
        }
    }
    | a=expr '<=' b=expr 
        {
        if($a.type.equals("int")){
            $code = $a.chaine;
            $code += $b.chaine;
            $code += printINFEQ();
        }
        else{
            $code = $a.chaine;
            $code += $b.chaine;
            $code += printFINFEQ();
        }
        }
    | a=expr '>=' b=expr 
        {
        if($a.type.equals("int")){
            $code = $a.chaine;
            $code += $b.chaine;
            $code += printSUPEQ();
        }
        else{
            $code = $a.chaine;
            $code += $b.chaine;
            $code += printFSUPEQ();
        }
        }
    //gestion des operateurs logiques 
    | c=condition 'and' d=condition
    {   
        $code = $c.code;
        $code += $d.code;
        $code += printMUL();
        $code += printPUSHI("0");
        $code += printNEQ(); 
    }
    |  c=condition 'or' d=condition
    {   
        $code = $c.code;
        $code += $d.code;
        $code += "ADD\n";
        $code += printPUSHI("0");
        $code += printNEQ(); 
    }
    | 'not'  c=condition
    {   
        $code = $c.code;
        $code += printPUSHI("0");
        $code += printEQUAL(); 
    }
    ;


assignation returns [ String code ]  // L'assignation d'une valeur à une variable
    : IDENTIFIANT '=' expr  // On assigne une expression à une variable
        {  
            String identifiant = $IDENTIFIANT.text;
            VariableInfo vi = tablesSymboles.getVar(identifiant);  // On récupère les infos de la variable
            int varAddress = vi.address;
            String type = vi.type;
            if (vi == null) {  // Si la variable n'est pas déclarée, erreur
                throw new IllegalArgumentException("Variable non déclarée : " + identifiant);
            }
            
            String exprCode = $expr.chaine;
            $code = exprCode;
            if (type.equals("int")){
                if (vi.scope == VariableInfo.Scope.GLOBAL){
                    $code += printStoreG(varAddress);
                }
                else if (vi.scope == VariableInfo.Scope.LOCAL){
                    $code += printSTOREL(varAddress);
                }
                else if (vi.scope == VariableInfo.Scope.PARAM){
                    $code += printSTOREL(varAddress);
                }
            }
            else{
                if (vi.scope == VariableInfo.Scope.GLOBAL){
                    $code += printStoreG(varAddress+1);
                    $code += printStoreG(varAddress);
                }
                else if (vi.scope == VariableInfo.Scope.LOCAL){
                    $code += printSTOREL(varAddress+1);
                    $code += printSTOREL(varAddress);
                }
                else if (vi.scope == VariableInfo.Scope.PARAM){
                    $code += printSTOREL(varAddress+1);
                    $code += printSTOREL(varAddress);
                }
            }
              // On stocke le résultat dans la variable
        }
    | IDENTIFIANT '+=' expr  // On fait une addition à une variable (a += b)
        {  
            String identifiant = $IDENTIFIANT.text;
            VariableInfo vi = tablesSymboles.getVar(identifiant);  // Récupère les infos de la variable
            String type = vi.type;
            int varAddress = vi.address;
            if (vi == null) {  // Si la variable n'est pas déclarée, erreur
                throw new IllegalArgumentException("Variable non déclarée : " + identifiant);
            }
            
            String exprCode = $expr.chaine;

            if (type.equals("int")) { 
                if (vi.scope == VariableInfo.Scope.GLOBAL){
                    $code = printPushG(varAddress);
                }
                else if (vi.scope == VariableInfo.Scope.LOCAL){
                    $code = printPUSHL(varAddress);
                }
                else if (vi.scope == VariableInfo.Scope.PARAM){
                    $code = printPUSHL(varAddress);
                }   
                    $code += exprCode;  // On ajoute l'expression
                    $code += printADD(); 
                if (vi.scope == VariableInfo.Scope.GLOBAL){
                    $code += printStoreG(varAddress);
                }
                else if (vi.scope == VariableInfo.Scope.LOCAL){
                    $code += printSTOREL(varAddress);
                }
                else if (vi.scope == VariableInfo.Scope.PARAM){
                    $code += printSTOREL(varAddress);
                }
                }
            else if (type.equals("double")) {
                if (vi.scope == VariableInfo.Scope.GLOBAL){
                    $code = printPushG(varAddress);
                    $code += printPushG(varAddress+1);
                }
                else if (vi.scope == VariableInfo.Scope.LOCAL){
                    $code = printPUSHL(varAddress);
                    $code += printPUSHL(varAddress+1);
                }
                else if (vi.scope == VariableInfo.Scope.PARAM){
                    $code = printPUSHL(varAddress);
                    $code += printPUSHL(varAddress+1);
                }   
                    $code += exprCode;  // On ajoute l'expression
                    $code += printADD(); 
                if (vi.scope == VariableInfo.Scope.GLOBAL){
                    $code += printStoreG(varAddress+1);
                    $code += printStoreG(varAddress);
                }
                else if (vi.scope == VariableInfo.Scope.LOCAL){
                    $code += printSTOREL(varAddress+1);
                    $code += printStoreG(varAddress);
                }
                else if (vi.scope == VariableInfo.Scope.PARAM){
                    $code += printSTOREL(varAddress+1);
                    $code += printStoreG(varAddress);
                }
            }
            
        }
;
    
input returns [ String code ]  // Instruction de lecture d'une valeur
   : 'input' '(' IDENTIFIANT ')'
     { 
         String identifiant = $IDENTIFIANT.text;
         VariableInfo vi = tablesSymboles.getVar(identifiant);  // On récupère l'info de la variable
         if (vi == null) {
             throw new IllegalArgumentException("Variable non déclarée : " + identifiant);  // Erreur si non déclarée
         }
         String type = vi.type;
         int varAddress = vi.address;
        if (type.equals("int")){
            $code = printREAD();
            if (vi.scope == VariableInfo.Scope.GLOBAL){
                    $code += printStoreG(varAddress);
                }
            else if (vi.scope == VariableInfo.Scope.LOCAL){
                    $code += printSTOREL(varAddress);
                }
            else if (vi.scope == VariableInfo.Scope.PARAM){
                    $code += printSTOREL(varAddress);
                }
        }
        else{
            $code = printREADF();
            if (vi.scope == VariableInfo.Scope.GLOBAL){
                $code += printStoreG(varAddress+1);
                $code += printStoreG(varAddress);
            }
        else if (vi.scope == VariableInfo.Scope.LOCAL){
                $code += printSTOREL(varAddress+1);
                $code += printSTOREL(varAddress);
            }
        else if (vi.scope == VariableInfo.Scope.PARAM){
                $code += printSTOREL(varAddress+1);
                $code += printSTOREL(varAddress);
            }
        }

         
     }
;

output returns [ String code ]  // Instruction d'affichage d'une valeur
    : 'output' '(' IDENTIFIANT ')'
        {
            String identifiant = $IDENTIFIANT.text;
            VariableInfo vi = tablesSymboles.getVar(identifiant);  // On récupère l'info de la variable
            if (vi == null) {
                throw new IllegalArgumentException("Variable non déclarée pour output : " + identifiant);  // Erreur si non déclarée
            }
            int varAddress = vi.address;
            String type = vi.type;
             
             // On charge la valeur de la variable
            if (type.equals("int")){
                if (vi.scope == VariableInfo.Scope.GLOBAL){
                    $code = printPushG(varAddress);
                }
                else if (vi.scope == VariableInfo.Scope.LOCAL){
                    $code = printPUSHL(varAddress);
                }
                else if (vi.scope == VariableInfo.Scope.PARAM){
                    $code = printPUSHL(varAddress);
                } 
                $code += printWRITEPOP();
            }
            else{
                if (vi.scope == VariableInfo.Scope.GLOBAL){
                    $code = printPushG(varAddress);
                    $code += printPushG(varAddress+1);
                }
                else if (vi.scope == VariableInfo.Scope.LOCAL){
                    $code = printPUSHL(varAddress);
                    $code += printPUSHL(varAddress+1);
                }
                else if (vi.scope == VariableInfo.Scope.PARAM){
                    $code = printPUSHL(varAddress);
                    $code += printPUSHL(varAddress+1);
                } 
                    $code += printWRITEFPOPPOP();
                }

        }
        
        |  'output' '(' expr ')'
        {
            String type = $expr.type;
            String exprCode = $expr.chaine;
            $code = exprCode;  // On affiche directement le résultat de l'expression
            if (type.equals("int")){$code += printWRITEPOP(); }// On fait un POP après l'affichage
            else{$code += printWRITEFPOPPOP();}
        }
    ;
//gestion des diiférentes boucles
boucle returns [ String code ]
    : 'while'  '('  condition ')' instruction //gestion des Whiles
        {   
            String labelDebut = newLabel();
            String labelFin = newLabel();
            $code = printLabel(labelDebut);
            $code += $condition.code;
            $code += printJumpF(labelFin);
            $code += $instruction.code;
            $code += printJump(labelDebut);
            $code += printLabel(labelFin);
        }
        //gestion des if avec ou sans else
    |'if' '(' condition ')' then=bloc elser=bloc {
        String labelDebut = newLabel();
        String labelFin = newLabel();
        $code = printLabel(labelDebut);   // Début du test
        $code += $condition.code;         // Évaluation de la condition
        String labelElse = newLabel();
        $code += printJumpF(labelElse);
        $code += $then.code;
        $code += printJump(labelFin); 
        $code += printLabel(labelElse); // Place l'étiquette de fin après le 'then'
        $code += $elser.code;
        $code += printJump(labelFin);
        $code += printLabel(labelFin); 
    }
    |'if' '(' condition ')' then=bloc {   
        String labelDebut = newLabel();
        String labelFin = newLabel();
        $code = printLabel(labelDebut);   // Début du test
        $code += $condition.code;         // Évaluation de la conditio
        $code += printJumpF(labelFin);
        $code += $then.code;              // Si la condition est vraie, on exécute 'then'
        $code += printLabel(labelFin);
        }
        //gestion des boucles for
    |'for' '(' first=assignation ';' condition ';' second=assignation ')' instruction{
        $code = $first.code;
        String labelDebut = newLabel();
        String labelFin = newLabel();
        $code += printLabel(labelDebut);
        $code += $condition.code;
        $code += printJumpF(labelFin);
        $code += $instruction.code;
        $code += $second.code;
        $code += printJump(labelDebut);
        $code += printLabel(labelFin);
    }
    ; 

params
    : TYPE IDENTIFIANT
        {
            tablesSymboles.addParam($IDENTIFIANT.text,$TYPE.text);

        }
        ( ',' TYPE IDENTIFIANT
            {
                tablesSymboles.addParam($IDENTIFIANT.text,$TYPE.text);
            }
        )*
    ;

args returns [ String code, int size] @init{ $code = new String(); $size = 0; }
    : ( expr 
    {   
        if($expr.type.equals("int")){
            $size++;
        }
        else{
            $size+=2;
        }
        $code += $expr.chaine;

    }
    ( ',' expr
    {
        if($expr.type.equals("int")){
            $size++;
        }
        else{
            $size+=2;
        }
        $code += $expr.chaine;
    }
    )*
      )?
    ;

fonction returns [ String code ]
@init{
    tablesSymboles.enterFunction();
    $code = new String(); 
}
@after{
    tablesSymboles.exitFunction();
}
    //gestion des fonctions sans paramettres
    : TYPE IDENTIFIANT '('  params ? ')'
        {
            String type = $TYPE.text;
            String identifiant = $IDENTIFIANT.text;
            tablesSymboles.addFunction(identifiant, type);
            $code = printLabel(identifiant);
        }
        '{' 
        NEWLINE?
        (decl { $code += $decl.code; })*
        NEWLINE*
        (  instruction  {$code += $instruction.code;} )*
        '}' 
        NEWLINE*
         { $code += "RETURN\n"; }
    ;

// Lexer - Définition des tokens pour la grammaire

NEWLINE : '\r'? '\n' ;  // Nouvelle ligne
WS : (' ' | '\t')+ -> skip;  // Espaces et tabulations sont ignorés
RETURN: 'return';
TYPE : 'int' | 'double' | 'bool';  // Types de données (entiers ou doubles)
FLOTTANT : ('0'..'9')+ '.' ('0'..'9')?;
ENTIER : ('0'..'9')+ ;  // Entiers
BOOL : ('True'|'False');
KEYWORDS : ('for'|'while');
IDENTIFIANT: [a-zA-Z_][a-zA-Z_0-9]* ;  // Identifiants (noms des variables)
COMMMENT : (('//' ~('\n')*) | ('/*' .*? '*/' )) -> skip;
