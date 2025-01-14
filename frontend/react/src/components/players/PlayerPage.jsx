import axios from "axios";
import {useEffect, useState} from "react";
import PlayerCard from "./PlayerCard.jsx";
import "./players.css";

export default function PlayerPage(){

    const [players, setPlayers] = useState([]);
    const [error, setError] = useState(null);


    async function getPlayers(){
        try {
            const response = await axios.get("http://localhost:8420/api/v1/players");
            setPlayers(response.data);
        } catch (error){
            setError(error);
            console.error(error);
        }
    }

    useEffect(() => {
        getPlayers();
    }, []);


    return (
        <main className={"player-page"}>
            <section className={"player-page__main"}>
                <article className={"player-page__article"}>
                    <h1 className={"player-page__heading"}>Players</h1>
                    {error && <p className={"player-page__error"}>{error}</p>}
                    <div className={"player-page__players-container"}>
                        {players.map(player => (
                            <PlayerCard key={player.id} player={player}/>
                        ))}
                    </div>
                </article>
            </section>
        </main>

    )

}