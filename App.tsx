import { View, Text } from 'react-native'
import React from 'react'
import { BackGroundServices } from './src'

const App = () => {
  const run=async()=>{
    console.log(".....")

  }
  return (
    <View>
      <BackGroundServices Title={'fsd'} Text={'df'} onEachSecond={5} killService={false}/>
      <Text>App</Text>
    </View>
  )
}

export default App