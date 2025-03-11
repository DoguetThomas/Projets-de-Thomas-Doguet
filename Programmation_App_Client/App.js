import React from 'react'
import {useState} from 'react'
import Navigation from './navigation/navigation'
import { TokenContext, UsernameContext } from './Context/Context'


export default function App () {
    const [token, setToken] = useState(null)
    const [username, setUsername] = useState(null)
    return (<UsernameContext.Provider value={[username, setUsername]}>
            <TokenContext.Provider value={[token, setToken]}>
              <Navigation />
            </TokenContext.Provider>
          </UsernameContext.Provider>
      )
  }