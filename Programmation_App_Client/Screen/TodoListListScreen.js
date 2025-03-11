import React, {useContext, useState, useEffect} from 'react'
import { useNavigation } from '@react-navigation/native';
//import de la feuille de style
import styles from '../style/style.js';

import { TokenContext, UsernameContext} from '../Context/Context'

//import des méthodes agissant sur l'api a l'aide des todolists
import {getTodoLists, createTodoList, deleteTodoList} from '../util/todoList'
import {Image, FlatList, View , Text, TextInput, Button, TouchableOpacity, ScrollView} from 'react-native-web';

export default function TodoListListScreen (){
    //création dun compteur de liste et de son mutateur
    const [count, setCount] = useState(0)
    //recuperation du token de l'utilisateur
    const [token, setToken] = useContext(TokenContext);
    //recuperation de l'username de l'utilisateur
    const [username, setUsername] = useContext(UsernameContext);
    //création d'une liste de todoList, qui sera vide au debut, et de son mutateur
    const [todoLists, setTodoList] = useState([]);
    //creation d'une variable permettant de recuperer le nom de la nouvelle todoList lors de la creation de l'une d'ell
    //et de son mutateur
    const [newTodoListText, setNewTodoListText] = useState('')
    const navigation = useNavigation();

    //recuperation des differentes listes de todo liste de l'utilisateur
    useEffect(() => {
        const fetchTodoLists = async () => {
        try {
          //recuperation des differentes listes de todo liste de l'utilisateur grace a l'api
          //grace a ses informations
          const todoLists = await getTodoLists(username, token);
          //mise en place de la liste recuperée
          setTodoList(todoLists);
          //mise en place du compteur de liste
          setCount(todoLists.length)
        
        } catch (error) {//dans la cas d'une erreur, l'afficher
            console.error("Erreur lors de la récupération des Todo Lists:", error);
        }
        };
       
        fetchTodoLists();
        }, [username, token]);

    //méthode pour ajouter(créer une nouvelle liste)
    const addTodoList = async () => {
        try {
            //creation et recuperation de la nouvelle todoList
            const newTodoList = await createTodoList(username, newTodoListText, token);
            //ajout de la nouvelle liste dans la liste de liste
            setTodoList((prevTodoLists) => [
                ...prevTodoLists,
                { id: newTodoList.id, title: newTodoList.title }, 
            ]);
            //remise a 0 de du nouveau texte
            setNewTodoListText('');
            //ajout de 1 dans le nombre de liste
            setCount(count + 1);
        } catch (e) {//dans la cas d'une erreur, l'afficher
            console.error('Erreur lors de la création de la liste', e);
        }
    };
    //méthode permettant de supprimer une liste
    const supTodoList = async (list) => {
        try {
            //suppression de a liste dans l'api a l'aide de son id, et du token de connexion
            await deleteTodoList(list.id, token);
            //supression de la liste suprimée dans l'api dans la liste de liste
            setTodoList(prevTodoLists => 
                prevTodoLists.filter(item => item.id !== list.id)  
            );
            //elever 1 du nombre de liste
            setCount(count - 1);
        } catch (e) {//dans la cas d'une erreur, l'afficher
            console.error('Erreur lors de la suprression de la liste', e);
        }
    };

    //methode permettant de naviger vers l'ecran d'une liste en fonction d'une liste donnée en paramettre
    const handleNavigate = (item) => {
        navigation.navigate('Todos', { liste: item }); 
      };
    
    //l'ecran contiendra 
    //-un compteur de liste(qui s'affiche en direct)
    //-un formulaire pour l'ajout d'une liste aisi qu'un bouton pour soumettre ce formulaire
    // et d'une liste affichant toutes les listes, permetant de la supprimer si besoin
    return (
        <View>
            <Text>Nombre de liste = {count}</Text>
            <View>
                <TextInput
                    style = {styles.textInputer}
                    onChangeText={setNewTodoListText}
                    placeholder="Saisir ici un nouvel item"
                    placeholderTextColor="rgba(0, 0, 0, 0.5)"
                    onSubmitEditing={addTodoList}
                    value={newTodoListText}
                />
            </View>
            <View>
                <TouchableOpacity style={styles.addingbutton} onPress={addTodoList}>
                    <Text style={styles.buttonText}>Ajouter une nouvelle liste</Text>
                </TouchableOpacity>
            </View>
            <ScrollView style={{ maxHeight: 300 }}>
                <FlatList 
                    data={todoLists}
                    renderItem={({ item }) => (
                        <View style = {styles.element}>
                            <Text>{item.title}</Text>
                            <TouchableOpacity onPress={() => supTodoList(item)}>
                                <Image
                                    source={require('../assets/trash-can-outline.png')}
                                    style={{ height: 24, width: 24 }}
                                />
                            </TouchableOpacity>
                            <Button title="Voir la liste" onPress={() => handleNavigate(item)} />
                        </View>
                    )}
                />
            </ScrollView>
            
        </View>
    );

}
