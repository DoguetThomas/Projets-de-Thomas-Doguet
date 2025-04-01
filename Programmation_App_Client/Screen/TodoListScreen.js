import React, {useContext, useState, useEffect} from 'react'

import { ScrollView } from 'react-native-web';

//import de la feuille de style
import styles from '../style/style.js';

import { TokenContext} from '../Context/Context'


import { View , FlatList, TextInput,Text, TouchableOpacity, Image, Switch} from 'react-native-web';

//import des méthodes agissant sur l'api au niveaux des items
import { getTodos, createTodo, updateTodo, deleteTodo } from '../util/todo'

export default function TodoListScreen ({route}){
    //recuperation de la liste en question
    const { liste } = route.params;
    //recuperation du token de l'utilisateur connecté
    const [token, setToken] = useContext(TokenContext);
    //creation d'une liste vide d'actions et de son mutateur
    const [todos, setTodos] = useState([]);
    //creation d'une variable qui permettra de stocker le nom de la nouvelle action lors de la création de celle ci
    const [newTodoText, setNewTodoText] = useState('');
    //creation d'un compteur d'action A REALISER et de son mutateur
    const [count, setCount] = useState(0)
    //un etat de filtre, qui est pour l'instant all
    const [filter, setFilter] = useState("all");

    //methode de recuperation des actions de la todo liste
    useEffect(() => {
        const fetchTodos = async () => {
        try {
          //recuperation des actions de la liste(realisées ou non) grace a l'id de la liste et le token de connexion de l'utilisateur
          const todo = await getTodos(liste.id, token);
          //mise en place de la liste d'actions a l'aide ce qui est recuperé
          setTodos(todo);
          //mise en place du compteur
          setCount(todo.length);
        } catch (error) {//dans la cas d'une erreur, l'afficher
            console.error("Erreur lors de la récupération des Todo Lists:", error);
        }
        };
       
        fetchTodos();
        }, [token, liste.id]);

    //methode de creation d'une action
    const addTodo = async () => {
        try {
            //utilisation de la methode agissant sur l'api, qui crée une action, avec son texte,
            //l'id de la liste currente, et le token de connexion de l'utilisateur
            const newTodo = await createTodo(newTodoText, liste.id, token);
            //actualisation de la liste d'action
            setTodos((prevTodo) => [
                ...prevTodo,
                { id: newTodo.id, title: newTodo.title , content: newTodo.content, done : false}, 
            ]);
            //remise a 0 du nom de l'action
            setNewTodoText('');
            //actualisation du nombre d'action a realiser( car l'action n'est pas ralisée initialement )
            setCount(count + 1);

        } catch (e) {//dans la cas d'une erreur, l'afficher
            console.error('Erreur lors de la création de la tache', e);
        }
    };

    //methode de suppresion d'une action
    const supTodo = async (action) => {
        try {
            //utilisation de la methode agissant sur l'api, qui supprime une action, avec son ID,
            // et le token de connexion de l'utilisateur
            await deleteTodo(action.id, token);
            //actualisation de la liste d'action
            setTodos(prevTodo => {
                const updatedTodos = prevTodo.filter(item => item.id !== action.id);
                //actualisation du nombre d'action a realiser si l'action ne l'étais pas
                if (!action.done){
                    setCount(prevCount => prevCount - 1); 
                }
                return updatedTodos;
            });
        } catch (e) {//dans la cas d'une erreur, l'afficher
            console.error('Erreur lors de la suppression de l\'action', e);
        }
    };

    //methode d'actualisation d'une action
    const uptTodo = async (action) => {
        try {
            //utilisation de la methode agissant sur l'api, qui actualise l'element done d'une action, avec son ID,
            // et le contraire du done actuel
            const updatedTodo = { ...action, done: !action.done };
            await updateTodo(updatedTodo.id, updatedTodo.done, token);
            
            //si le donne devient false, on ajoute une action au nombre d'action a realiser
            if (updatedTodo.done == false){
                setCount(count+1);
            }
            //sinon, retirer 1
            else{
                setCount(count-1);
            }
            //mise a jour de la liste d'action
            setTodos(prevTodos => 
                prevTodos.map(todo =>
                    todo.id === updatedTodo.id ? updatedTodo : todo
                )
            );
        } catch (e) {//dans la cas d'une erreur, l'afficher
            console.error('Erreur lors de la mise à jour de l\'action', e);
        }
    };

    //recuperation des action en fonction du filter, 
    //-si filter vaut completed on recupere seulement les actions realisées
    //-si filter vaut incomplete on recupere seulement les actions non-realisées
    //-sion les retourner toutes
    const filteredTodos = todos.filter(todo => {
        if (filter === "completed") return todo.done;
        if (filter === "incomplete") return !todo.done;
        return true;
    });

    //calcul du nombre d'actions completées
    const completedTasks = todos.filter(todo => todo.done).length;
    //calucl du nombre d'action total
    const totalTasks = todos.length;
    //calcul de la proportion d'actions realisées
    const progress = totalTasks > 0 ? completedTasks / totalTasks : 0;

    //l'ecran contiendra 
    //-un titre(le nom de la liste)
    //-une barre de progression des taches
    //-un compteur d'action non-réalisées
    //-un formulaire d'ajout d'une action, aisi q'un bouton pour soumettre ce formulaire
    //-une suite de bouton pour afficher toutes les action -, seulement les realisées, et seulement les non realisées
    //-une liste d'acton a realiser, accompagné d'un switch pour indiquer si elle est effectuée ou non
    //et d'une poubelle pour la supprimer
    return (
        <View>
            <View style = {styles.viewtitre}>
                <Text style = {styles.titreListe}>{liste.title}</Text>
            </View>
            <View style={{ marginVertical: 5 }}>
                <Text>Progression des tâches réalisées :</Text>
                <div style={{
                    width: '100%',
                    height: '20px',
                    backgroundColor: '#e0e0e0',
                    borderRadius: '10px',
                    overflow: 'hidden',
                }}>
                    <div style={{
                        width: `${progress * 100}%`,
                        height: '100%',
                        backgroundColor: '#4caf50',
                    }}></div>
                </div>
                <Text>{`${completedTasks} sur ${totalTasks} tâches terminées`}</Text>
            </View>
            <Text>Nombre d'action non réalisées = {count}</Text>
            <View>
                <TextInput
                    style = {styles.textInputer}
                    onChangeText={setNewTodoText}
                    placeholder="Saisir ici une nouvelle action"
                    placeholderTextColor="rgba(0, 0, 0, 0.5)"
                    onSubmitEditing={addTodo}
                    value={newTodoText}
                />
            </View>
            <View>
                <TouchableOpacity style={styles.addingbutton} onPress={addTodo}>
                    <Text style={styles.buttonText}>Ajouter une nouvelle action</Text>
                </TouchableOpacity>
            </View> 
            <View style={styles.container}>
                <TouchableOpacity style={styles.button} onPress={() => setFilter("all")}>
                    <Text style={styles.buttonText}>Toutes</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.button} onPress={() => setFilter("completed")}>
                    <Text style={styles.buttonText}>Complétées</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.button} onPress={() => setFilter("incomplete")}>
                    <Text style={styles.buttonText}>Non complétées</Text>
                </TouchableOpacity>
            </View>
            <ScrollView style={{ maxHeight: 150 }}>
                <FlatList 
                    data={filteredTodos}
                    keyExtractor={item => item.id.toString()}
                    renderItem={({ item }) => (
                        <View style = {styles.element}>
                            <Text>{item.content}</Text>
                            <Switch value={item.done} onValueChange={() => uptTodo(item)} />
                            <TouchableOpacity onPress={() => supTodo(item)}>
                                <Image
                                    source={require('../assets/trash-can-outline.png')}
                                    style={styles.trash}
                                />
                            </TouchableOpacity>
                        </View>
                    )}
                />
            </ScrollView>
                   
        </View>
    )
}

