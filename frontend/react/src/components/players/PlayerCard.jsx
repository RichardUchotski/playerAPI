import {getRandomUserData} from "../../data/apiCalls.js"

export default function PlayerCard({player}){

    const {id, firstName, lastName, age, email} = player;

    const randomUserData = getRandomUserData("Male",1);


    return (
        <div className={"player-card"}>
            <p>{firstName}</p>
            <p>{lastName}</p>
            <p>{age}</p>
            <p>{email}</p>
            <p>{id}</p>
        </div>
    )

}

