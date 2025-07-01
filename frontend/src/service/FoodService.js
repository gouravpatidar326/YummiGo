import axios from "axios";

const backendUrl = import.meta.env.VITE_BACKEND_URL

export const fetchFoodList = async () => {
    try {
        const response = await axios.get(backendUrl+'/food/getFoods');
        return response.data
    } catch (error) {
        console.log('Error fetching food List: ', error)
        throw error;
    }
}

export const fetchFoodDetails = async (id) => {
    try {
        const response = await axios.get(backendUrl+"/food/"+id);
        return response.data;
        
    } catch (error) {
        console.log('Error fetching food details:', error);
        throw error;
    }
}