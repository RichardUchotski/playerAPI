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

export const saveCustomer = async (playerData) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/players`, playerData)
    } catch (e){
        throw e;
    }
}

export const deleteAllCustomers = async () => {
    try {
        return await axios.delete(`${import.meta.env.VITE_API_BASE_URL}/api/v1/players`)
    } catch (e){
        throw e;
    }
}

export const deletePlayerById = async (id) => {
    try {
        return await axios.delete(`${import.meta.env.VITE_API_BASE_URL}/api/v1/players/${id}`)
    } catch (e){
        throw e;
    }
}


export const updatePlayer = async (id, playerData) => {
    try {
        return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/api/v1/players/${id}`, playerData)
    }catch (e){
        throw e;
    }
}