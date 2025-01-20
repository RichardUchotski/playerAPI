import axios from "axios";

export const getRandomUserData = async (gender,id) => {

    try{
        const response = await axios.get("https://randomuser.me/api/?results=1&gender=female");
        console.log(response.data);
        return response.data;
    } catch (error){
        console.error("Error fetching random user data: ", error);
        throw error;
    }

}