import "./registration.css";
import FormItem from "./FormItem.jsx";
import Teams from "../../data/teamdata.json";

export default function PlayerRegistrationPage(){

    Teams.sort((firstName, secondName) => {
        if(firstName.teamName < secondName.teamName) return -1;
        if(firstName.teamName > secondName.teamName) return 1;
        return 0;
    })


    return (
        <main className={"main-form-wrapper"} >
                <section>
                    <article className={"form-information"} >
                        <h1 className={"form-information__header"} >Player Registration Form</h1>
                        <br/>

                     <p className={"form-information__paragraph"}> If you’re looking to join the vibrant and welcoming community of Scottish Korfball,
                            registration is the first step toward becoming part of this exciting mixed-gender sport.
                            Whether you’re a seasoned player or a complete beginner, Scottish Korfball offers a unique
                            experience that combines teamwork, strategy, and physical fitness in a supportive and
                            inclusive environment.</p>
                        <br/>
                     <p className={"form-information__paragraph"}> By registering, you gain access to training sessions, leagues, and
                            tournaments that bring together players from across Scotland, offering a chance to develop
                            your skills, meet new people, and compete in a dynamic and fast-paced game.
                            Register today and become part of the growing Scottish Korfball family. </p>
                        <br/>

                    </article>
                    <article>
                        <form className={"form-container"} action={"http://localhost:8420/api/v1/players"} method={"POST"}>
                            <FormItem inputType={"text"} placeholder={"Enter first name"}>First Name: </FormItem>
                            <FormItem inputType={"text"} placeholder={"Enter last name"}>Last Name: </FormItem>
                            <FormItem inputType={"number"} placeholder={"Enter age"}>Age :</FormItem>
                            <FormItem inputType={"date"} placeholder={""}>Date of Birth: </FormItem>
                            <FormItem inputType={"tel"} placeholder={"Enter phone number"}>Phone :</FormItem>
                            <FormItem inputType={"email"} placeholder={"Enter email"}>Email :</FormItem>
                            <FormItem inputType={"select-gender"} placeholder={""}>Gender: </FormItem>
                            <FormItem inputType={"select-team"} placeholder={""} teams={Teams}>Team: </FormItem>
                            <FormItem inputType={"checkbox"} placeholder={""}>I agree to the terms and conditions of
                                scottish korfball</FormItem>
                            <FormItem inputType={"submit"}/>
                        </form>
                    </article>
                </section>

        </main>
    )

}