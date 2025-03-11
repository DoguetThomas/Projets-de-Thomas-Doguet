import React from 'react'
import { TokenContext, UsernameContext } from '../Context/Context'
import {useState, useContext} from 'react'
import {View, Text, TextInput,TouchableOpacity} from 'react-native'
//import de la methode qui crée un utilisateur dans l'api
import {signUp} from '../util/SignIn'
//import de la feuille de style
import styles from '../style/style.js';


export default function SignUpScreen ({ navigation }) {
    //mise en place de l'username qui sera recupéré par le formulaire et de son mutateur
    const [usernameInput, setUsernameInput] = useState('');
    //mise en place du mot de passe qui sera recupéré par le formulaire et de son mutateur
    const [passwordInput, setPasswordInput] = useState('');
    //utilisation et gestion des erreurs
    const [error, setError] = useState(null);
    //mise en place du pseudo, qui est nul car nous sommes deconnecté
    const [token, setToken] = useContext(TokenContext);
    //mise en place du token, qui est nul car nous sommes deconnecté
    const [username, setUsername] = useContext(UsernameContext);

    //méthode pour tenter de connecter
    const handleSignUp = async () => {
      //si un des champs n'est pas rempli, création d'une erreur
        if (usernameInput === '' || passwordInput === '') {
            setError('tout les champs doivent etre remplis');
            return;
          }
          try {
            //recuperation du token crée lors de la creation du compte
            const tokenResponse = await signUp(usernameInput, passwordInput);
            
            if (tokenResponse) {
              //mise en place du token
              setToken(tokenResponse);
              //mise en place de l'username
              setUsername(usernameInput);
              //redirection vers l'ecran d'acceuil connecté
              navigation.navigate('Home');
            }
          } catch (e) {
            //mise en place d'un errreur lors de la creation du compte
            console.log(e);
            setError('An error occurred during sign-up');
  }
    };
    //ecran permettant de créer un compte avec un formulaire
    //un bouton pour soumettre le formulaire
    //un bouton pour se connecter, et non s'inscricre
    return (
      <View>
        <Text style = {styles.textSignPage}>Creer un compte</Text>
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
          <TouchableOpacity style={styles.addingbutton} onPress={() =>handleSignUp()}>
                    <Text style={styles.buttonText}>Creer un compte</Text>
          </TouchableOpacity>
          <Text style = {styles.textSignPage}>deja un compte ?</Text>
          <TouchableOpacity style={styles.addingbutton} onPress={() => navigation.navigate('SignIn')}>
                    <Text style={styles.buttonText}>Se connecter</Text>
          </TouchableOpacity>
      </View>
      );
  }