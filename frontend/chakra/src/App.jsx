import {Spinner, Stack, Text, Wrap, WrapItem} from '@chakra-ui/react'
import SidebarWithHeader from "./components/shared/SideBar.jsx";
import {useEffect, useState} from "react";
import {getCustomers} from "./services/cleint.js";
import CardWithImage from "./components/Card.jsx";
import DrawerForm from "./components/DrawerForm.jsx";
import {errorNotification} from "./services/notification.js";

function App() {

    console.log("VITE_API_BASE_URL:", import.meta.env.VITE_API_BASE_URL);


    const [players,setPlayers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [err, setError] = useState("");

    const fetchPlayers = () => {
        setLoading(true);
        setTimeout(() => getCustomers().then(res => {
            setPlayers(res.data);
        }).catch(error => {
                if (error.response) {
                    console.error("Error Data:", error.response.data);
                    console.error("Status Code:", error.response.status);
                    console.error("Headers:", error.response.headers);
                } else if (err.request) {
                    console.error("No response received:", error.request);
                } else {
                    console.error("Axios error:", error.message);
                }
                setError(error.response.data.message);
                errorNotification(error.code, error.response.data.message);
        }
        ).finally(
            () => setLoading(false)), 0)
    }

    useEffect(() => {
        fetchPlayers();
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

    if(err){
        return <Stack>
            <Text>Opps there was an error</Text>
            <DrawerForm isApp={true} fetchPlayers={fetchPlayers} />
        </Stack>
    }


    if(players.length <=0){
        return <SidebarWithHeader>
            <Stack>
                <Text>No customers available</Text>
                <DrawerForm isApp={true} fetchPlayers={fetchPlayers} />
            </Stack>

        </SidebarWithHeader>
    }

  return (
     <SidebarWithHeader>
         <DrawerForm isApp={false} fetchPlayers={fetchPlayers}/>
         <Wrap spacing={"1rem"} justify={"center"}>
            {players.map((player,index)=> (
                <WrapItem key={index}>
                    <CardWithImage fetchPlayers={fetchPlayers} {...player}>Player: {player.firstName}</CardWithImage>
                </WrapItem>
            ))}
         </Wrap>
     </SidebarWithHeader>

  )
}

export default App
