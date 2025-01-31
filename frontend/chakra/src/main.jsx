import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import {ChakraProvider} from "@chakra-ui/react";
import {createStandaloneToast} from "@chakra-ui/react";
const {ToastContainer} = createStandaloneToast();
import './index.css'
import App from './App.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
      <ChakraProvider>
          <App />
          <ToastContainer />
      </ChakraProvider>
  </StrictMode>,
)
