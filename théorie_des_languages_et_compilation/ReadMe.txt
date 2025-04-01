DOGUET Thomas

lien vers les consignes : https://terrier.users.greyc.fr/LangCompil/2025/TP5A9.html

Description de mon Travail:

	•	Étape B : Réalisation des calculs et des expressions. J’ai choisi de diviser le travail en fonctions. Par exemple, pour afficher l’ajout d’un entier (PUSHI Entier), j’ai créé la fonction printPUSHI. Pour les expressions, j’ai créé la fonction printExpr, qui va, en fonction de l’opérateur, afficher le bon code (MUL, DIV, MOD, ADD, SUB). J’ai également mis en place le lexeur, qui prend en compte les priorités, gère les commentaires, les entiers, les sauts de ligne et les caractères à ignorer. Enfin, j’ai mis en place les règles calcul (qui représente tout ce qui va être écrit), instructions (qui représentent une ligne de calcul), et expression (qui représente une étape, en prenant en compte les priorités des calculs et/ou les parenthèses, ou un entier).
	•	Étape C : Afin de traiter les variables, j’ai ajouté dans le lexeur les identifiants et les types, en prenant en compte les priorités. Ensuite, j’ai ajouté les règles déclarations (qui représentent une déclaration de variable globale, par exemple int x ou int x = 20), et les assignations (qui représentent la mise en place d’une valeur dans une variable déjà créée, par exemple x = 20 ou x = 10 + 10). J’ai ensuite ajouté les assignations parmi les instructions possibles et j’ai créé le calcul en considérant que toutes les déclarations doivent être effectuées avant les instructions. Pour cela, j’ai utilisé les classes fournies :
	•	VariableInfo
	•	TableSimple
	•	TablesSymboles
J’ai également créé d’autres fonctions de génération de code telles que printADD, printPushG, printSTOREG.
	•	Étape D : Pour le traitement des entrées/sorties, j’ai créé les règles Input (qui permet de lire l’entrée de l’utilisateur) et Output (qui permet d’afficher la valeur d’une variable ou le résultat d’une expression). Je me suis aidé des fonctions de génération de code. Il s’agit donc des appels input(variable) et output(variable/expression).
	•	Étape E : Pour cette étape, j’ai créé un type d’assignation contenant un +=, qui va assigner la variable à l’addition entre elle-même et une expression.
	•	Étape F : Pour le traitement de la boucle while, j’ai tout d’abord ajouté les scripts fournis pour la création des labels et la gestion des valeurs True et False. Ensuite, j’ai ajouté la section boucle qui détecte un élément sous la forme while (condition) instruction. J’y crée un label de début et un label de fin, et j’exécute le code en vérifiant d’abord la condition (que j’ai étoffée par la suite), puis j’exécute le script en fonction du résultat. Enfin, j’affiche le titre de fin à la fin de l’exécution. J’ai ajouté et utilisé toutes les fonctions auxiliaires nécessaires.
	•	Étape G : Pour le traitement des conditions de base, j’ai étoffé la section condition avec toutes les conditions demandées, en utilisant et en créant toutes les fonctions auxiliaires nécessaires.
	•	Étape H : Pour les expressions logiques, j’ai encore étoffé la partie condition, en ajoutant des expressions logiques selon les priorités. J’ai donc créé un système afin de vérifier si un élément est supérieur à 0, en créant et utilisant toutes les fonctions auxiliaires nécessaires.
	•	Étape I : Afin d’ajouter les branchements conditionnels, j’ai étoffé la règle boucle avec :
	•	if (condition) then = bloc else = bloc
	•	if (condition) then = bloc
(en respectant la priorité du premier sur le deuxième).
Je l’ai traité de manière similaire à la boucle while, sauf qu’il n’y a pas de retour en arrière dans ce cas.
	•	Étape J : Pour les boucles for, j’ai étoffé la règle boucle avec :
	•	for (first = assignation; condition; second = assignation) instruction.
J’ai ensuite traité la boucle de manière similaire à la boucle while, en ajoutant d’abord la première assignation et, à la fin, la seconde.
	•	Étape K1 : Pour les fonctions sans paramètre, j’ai ajouté le code donné sur le sujet et utilisé la classe TableSymbole pour stocker le type et le nom de la fonction. J’ai ajouté les différents principes vus en cours (notamment le Call).
	•	Étape K2 : J’ai ajouté les éléments de Paramètre et d’argument, et modifié le traitement des fonctions et des expressions pour qu’ils prennent en compte le traitement des paramètres, en utilisant les TablesSymboles (activées en mode fonction). J’ai également modifié le traitement des différentes variables en fonction de leur type (globale, locale, ou paramètres).
	•	Étape K3 : J’ai ajouté le traitement du return dans les instructions, j’y ai ajouté l’allocation de mémoire et la libération pour la variable de retour, et ajouté le traitement dans le lexeur pour le différencier d’un identifiant.
	•	Étape K4 : Pour cette étape, je n’ai pas changé grand-chose car j’avais déjà ajouté le traitement des variables locales lors de l’étape K2. J’ai aussi modifié la partie fonction pour qu’elle ne prenne plus un bloc mais une suite d’instructions (même chose, mais permettant de différencier les types de variables).
	•	Étape L : Pour le traitement des flottants, j’ai ajouté tout d’abord dans le lexeur, ensuite, j’ai créé le traitement de la place mémoire en fonction du type (2 pour un flottant, autant à désallouer ensuite). J’ai aussi ajouté la valeur de retour Type dans expr, ce qui m’a permis de différencier les types pour chaque cas.
	•	Étape M : Je n’ai pas eu d’amélioration à effectuer pour cette étape, tous les tests étaient validés.
	•	Étape O : Afin de détecter chaque différence, je me suis aidé de la variable de retour de expr type, ce qui m’a permis de différencier chaque expr afin d’effectuer les bonnes opérations (conçues pour flottant ou pour entier).
	•	Je n’ai ensuite pas réussi à mettre en place la gestion des booléens comme type. J’ai essayé, mais des problèmes de récursivité ont été rapidement un frein pour mon travail.
