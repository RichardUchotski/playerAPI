import axios from 'axios';

export const getCustomers = async () => {
    try{
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/players`)
    } catch(e){
        throw e;
    }
}

export const getGenderPicture = async (gender, id) => {
    try {
        return await axios.get(`https://randomuser.me/api/portrains/${gender}/${id}.jpg`)
    } catch(e){
        throw e;
    }
}