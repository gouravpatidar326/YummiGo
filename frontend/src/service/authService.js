import axios from "axios";

const backendUrl = import.meta.env.VITE_BACKEND_URL

export const registerUser = async (data) => {
    try {
        const response = await axios.post(backendUrl+'/api/register',data);
        return response
    } catch (error) {
        throw error;
    }
}

export const login = async (data) => {
    try {
        const response = await axios.post(backendUrl+'/api/login',data);
        return response
    } catch (error) {
        throw error;
    }
}