import {Button, ButtonGroup, Spinner, Text, Wrap, WrapItem} from '@chakra-ui/react'
import SidebarWithHeader from "./components/shared/SideBar.jsx";
import {useEffect, useState} from "react";
import {getCustomers} from "./services/cleint.js";
import CardWithImage from "./components/Card.jsx";

function App() {

    const [players,setPlayers] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);
        setTimeout(() => getCustomers().then(res => {
            console.log(res);
            setPlayers(res.data);

        }).catch(error =>
            console.log(error)).finally(
            () => setLoading(false)), 0)

    }, []);


    if(loading){
        return <SidebarWithHeader>
        <Spinner  thickness='4px'
            speed='0.65s'
            emptyColor='gray.200'
            color='blue.500'
            size='xl'
        />
        </SidebarWithHeader>
    }

    if(players.length <=0){
        return <SidebarWithHeader>
            <Text>No customers available</Text>
        </SidebarWithHeader>
    }

  return (
     <SidebarWithHeader>
         <Wrap spacing={"1rem"} justify={"center"}>
            {players.map((player,index)=> (
                <WrapItem key={index}>
                    <CardWithImage {...player}>Player: {player.firstName}</CardWithImage>
                </WrapItem>
            ))}
         </Wrap>
     </SidebarWithHeader>

  )
}

export default App
