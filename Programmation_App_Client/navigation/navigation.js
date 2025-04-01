import React, {useContext} from 'react'
import { NavigationContainer } from '@react-navigation/native'
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs'
import { TokenContext} from '../Context/Context'


import HomeScreen from '../Screen/HomeScreen'
import SignInScreen from '../Screen/SignInScreen'
import SignUpScreen from '../Screen/SignUpScreen'
import SignOutScreen from '../Screen/signOutScreen'
import TodoListListScreen from '../Screen/TodoListListScreen'
import TodoListScreen from '../Screen/TodoListScreen'

const Tab = createBottomTabNavigator()

export default function Navigation () {
  const [token] = useContext(TokenContext)
  return (
        <NavigationContainer>
          {token == null ? (
            <Tab.Navigator>
              <Tab.Screen name='Home' component={HomeScreen} />
              <Tab.Screen name='SignIn' component={SignInScreen} />
              <Tab.Screen name='SignUp' component={SignUpScreen} />
            </Tab.Navigator>
          ) : (
            <Tab.Navigator>
              <Tab.Screen name='Home' component={HomeScreen} />
              <Tab.Screen name='TodoLists' component={TodoListListScreen} />
              <Tab.Screen name='SignOut' component={SignOutScreen} />
              <Tab.Screen name='SignIn' component={SignInScreen} options={{tabBarButton :() =>null}}/>
              <Tab.Screen name='Todos' component={TodoListScreen} options={{tabBarButton :() =>null}}/>
            </Tab.Navigator>
          )}
        </NavigationContainer>
  )
}

