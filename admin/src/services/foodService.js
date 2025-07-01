import axios from 'axios'

const backendUrl = import.meta.env.VITE_BACKEND_URL

export const addFood = async (data, image) => {

    const formData = new FormData()
        formData.append('name', data.name);
        formData.append('description', data.description);
        formData.append('price', data.price);
        formData.append('category', data.category);
        formData.append('file', image)
    
        try {
            await axios.post(backendUrl+'/food/add', formData, { headers: { "Content-Type": "multipart/form-data" } })
        } catch (error) {
          console.log('Error', error);
          throw error;         
        }
}

export const getFoodList = async () => {
  try {
    const response = await axios.get(backendUrl+'/food/getFoods');
    return response.data;
  } catch (error) {
    console.log('Error fetching food list',error);
    throw error;
  }
}

export const deleteFood = async (foodId) => {
    try {
      const response = await axios.delete(backendUrl+'/food/'+foodId);
      return response.status === 204;
    } catch (error) {
      toast.error('Error occured while removing food');
      throw error;
    }
}