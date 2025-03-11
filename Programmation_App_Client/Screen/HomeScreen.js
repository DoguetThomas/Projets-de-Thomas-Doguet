import React , {useContext, Fragment}from 'react'
import { TokenContext, UsernameContext } from '../Context/Context'
import {View, Text, TouchableOpacity} from 'react-native'
//import de la feuille de style
import styles from '../style/style.js';

//ecran d'acceuil prenant en compte la presence ou non d'un token, si il n'y a pas de token
//un message specifique invitant a la connexion est affichée, sinon, un message personnalisé
//a la personne contant son pseudo est affiché
export default function HomeScreen ({ navigation }) {
  //recupération du pseudo
  const [username, setUsername] = useContext(UsernameContext);
  //recupération du token
  const [token, setToken] = useContext(TokenContext);
  return (
    <View>
      <strong>Bonjour,</strong>
      {token == null ? (
            <Fragment>
              <Text style={{marginTop : 10,}}>Vous n'êtes pas connecté. Connectez-vous ou inscrivez-vous grâce aux boutons ci-dessous :</Text>
              <View style = {{marginTop : 30,}}>
                <TouchableOpacity style={styles.addingbutton} onPress={() => navigation.navigate('SignUp')}>
                  <Text style={styles.buttonText}>Créer un compte</Text>
                </TouchableOpacity>

                <Text style={styles.textSignPage}>Déjà un compte ?</Text>
                <TouchableOpacity style={styles.addingbutton} onPress={() => navigation.navigate('SignIn')}>
                  <Text style={styles.buttonText}>Se connecter</Text>
                </TouchableOpacity>
              </View>
            </Fragment>
          ) : (
            <Fragment>
              <Text style={{marginTop : 10,}}>Vous étes connectés en tant que {username}, vous pouvez dès maintenant accéder a vos TodoLists grace au bandeau de navigation ci dessous :</Text>
              <TouchableOpacity style={styles.addingbutton} onPress={() => navigation.navigate('SignOut')}>
                <Text style={styles.buttonText}>Se deconnecter</Text>
              </TouchableOpacity>
            </Fragment>
          )}
    </View>
  )
}