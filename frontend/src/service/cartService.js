import axios from 'axios';

const backendUrl = import.meta.env.VITE_BACKEND_URL

export const addToCart = async (foodId, token) => {
    try {
        await axios.post(backendUrl+"/api/cart", {foodId}, {headers: { Authorization: `Bearer ${token}`}});
    } catch (error) {
        console.error('Error while adding the cart data', error);
    }
}

export const removeQtyFromCart = async (foodId, token) => {
    try {
        await axios.post(backendUrl+"/api/cart/remove", {foodId}, {headers: { Authorization: `Bearer ${token}`}});        
    } catch (error) {
        console.error('Error while removing qty from cart', error);
    }
}

export const getCartData = async (token) => {
    try {
        const response = await axios.get(backendUrl+"/api/cart", 
            {headers: { Authorization: `Bearer ${token}`}}
        );
        return response.data.items;
    } catch (error) {
        console.error('Error while fetching the cart data', error);
    }
}