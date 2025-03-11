import React, { useContext } from 'react';
import { View, Text, TouchableOpacity } from 'react-native';
import { TokenContext, UsernameContext } from '../Context/Context';
//import de la feuille de style
import styles from '../style/style.js';

//ecran permettant de se deconnecter
export default function SignOutScreen({ navigation }) {
  //metuateur du token
  const [token, setToken] = useContext(TokenContext);
  //recuperation de l'username et de son mutateur
  const [username, setUsername] = useContext(UsernameContext);

  //methode de deconnexion
  const handleSignOut = () => {
    //remise a 0 du token
    setToken(null);
    //remise a 0 de l'username
    setUsername(null);
    //redirection vers l'ecran de connexion
    navigation.navigate('SignIn');
  };

  //affichage d'un message de confiramtion, suivi d'un bouton qui activait la méthode précédente
  return (
    <View>
      <Text style = {styles.textSignPage}>Cette page est une page de deconnexion, etes vous sur de vouloir vous deconnecter ? {username}?</Text>
      <TouchableOpacity style={styles.addingbutton} onPress={() =>handleSignOut()}>
        <Text style={styles.buttonText}>Se deconnecter</Text>
      </TouchableOpacity>
    </View>
  );
}