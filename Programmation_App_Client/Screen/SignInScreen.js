import React from 'react'
import { TokenContext, UsernameContext } from '../Context/Context';
import {useState, useContext} from 'react'
import {View, Text, TextInput, TouchableOpacity} from 'react-native'
//recupérations des requetes de l'api pour la connexion
import {signIn} from '../util/SignIn';
//import de la feuille de style
import styles from '../style/style.js';

export default function SignInScreen ({ navigation }) {
    //mise en place de l'username qui sera recupéré par le formulaire et de son mutateur
    const [usernameInput, setUsernameInput] = useState('');
    //mise en place du mot de passe qui sera recupéré par le formulaire et de son mutateur
    const [passwordInput, setPasswordInput] = useState('');
    //mise en place du token, qui est nul car nous sommes deconnecté
    const [token, setToken] = useContext(TokenContext);
    //mise en place du pseudo, qui est nul car nous sommes deconnecté
    const [username, setUsername] = useContext(UsernameContext);

    //méthode pour tenter de connecter
    const handleSignIn = async () => {
      try {
        //recuperation du token a l'aide du mot de passe et du pseudo rentré(s'ils existent deja)
        const tokenResponse = await signIn(usernameInput, passwordInput);
        //mise en place du token
        setToken(tokenResponse); 
        //mise en place de l'username
        setUsername(usernameInput); 
        //redirection vers l'acceuil ( ici, l'acceuil sera en mode connécté)
        navigation.navigate('Home');
         
      } catch (e) {
        console.log(e);      }//affichage de l'erreur si une erreue a ete retournée
    };
    
    //affichage du formulaire, et d'un bouton pour envoyer les données
    return (
      <View>
      <Text style = {styles.textSignPage}>Se connecter</Text>
        <TextInput
          style={styles.textInputer}
          placeholder="nom d'utilisateur"
          placeholderTextColor="rgba(0, 0, 0, 0.5)"
          value={usernameInput}
          onChangeText={setUsernameInput}
        />
        <TextInput
          style={styles.textInputer}
          placeholder="mot de passe"
          placeholderTextColor="rgba(0, 0, 0, 0.5)"
          value={passwordInput}
          secureTextEntry
          onChangeText={setPasswordInput}
        />
        <TouchableOpacity style={styles.addingbutton} onPress={() =>handleSignIn()}>
                    <Text style={styles.buttonText}>Se connecter</Text>
          </TouchableOpacity>
          <Text style = {styles.textSignPage}>Vous n'avez pas encore de compte ?</Text>
          <TouchableOpacity style={styles.addingbutton} onPress={() => navigation.navigate('SignUp')}>
                    <Text style={styles.buttonText}>Creer un compte</Text>
          </TouchableOpacity>
    </View>
      );
  }