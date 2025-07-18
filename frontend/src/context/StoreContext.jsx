import { createContext, useEffect, useState } from "react";
import { fetchFoodList } from "../service/FoodService";
import { addToCart, getCartData, removeQtyFromCart } from "../service/cartService";

const backendUrl = import.meta.env.VITE_BACKEND_URL;

export const StoreContext = createContext(null);

export const StoreContextProvider = (props) => {

    const [foodList, setFoodList] = useState([]);
    const [quantities, setQuantities] = useState({});
    const [token, setToken] = useState("");

    const increaseQty = async (foodId) => {
        setQuantities((prev) => ({ ...prev, [foodId]: (prev[foodId] || 0) + 1 }));
        await addToCart(foodId, token);
    }

    const decreaseQty = async (foodId) => {
        setQuantities((prev) => ({ ...prev, [foodId]: prev[foodId] > 0 ? prev[foodId] - 1 : 0 }));
        await removeQtyFromCart(foodId, token);
    }

    const removeFromCart = (foodId) => {
        setQuantities((prevQuantities) => {
            const updatedQuantities = { ...prevQuantities };
            delete updatedQuantities[foodId];
            return updatedQuantities;
        });
    }

    const loadCartData = async (token) => {
        const items = await getCartData(token);
        if (items) setQuantities(items);
        else setQuantities({}); 
    };


    const contextValue = {
        foodList,
        increaseQty,
        decreaseQty,
        quantities,
        removeFromCart,
        token,
        setToken,
        setQuantities,
        loadCartData
    };

    useEffect(() => {
        async function loadData() {
            const data = await fetchFoodList()
            console.log("Fetched food data:", data);
            setFoodList(data);
            if (localStorage.getItem("token")) {
                setToken(localStorage.getItem("token"));
                await loadCartData(localStorage.getItem("token"));
            }
        }
        loadData();
    }, []);

    return (
        <StoreContext.Provider value={contextValue}>
            {props.children}
        </StoreContext.Provider>
    )
}