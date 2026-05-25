import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import './App.css'
import Example from './components/Example'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <Example/>
    </>
  )
}

export default App
