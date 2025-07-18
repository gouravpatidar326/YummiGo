import React, { useContext, useState } from 'react'
import "./PlaceOrder.css"
import { assets } from '../assets/assets'
import { StoreContext } from '../context/StoreContext';
import { calculateCartTotals } from '../utils/CartUtils';
import axios from 'axios';
import { toast } from 'react-toastify';
import { RazorpayKey } from '../utils/contants';
import { useNavigate } from 'react-router-dom';


const backendUrl = import.meta.env.VITE_BACKEND_URL

const PlaceOrder = () => {
    const { foodList, quantities, setQuantities, token } = useContext(StoreContext);
    const navigate = useNavigate();

    const [data, setData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        phoneNumber: '',
        address: '',
        state: '',
        city: '',
        pin: ''
    })

    const onChangeHandler = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        setData(data => ({ ...data, [name]: value }));
    }

    const onSubmitHandler = async (event) => {
        event.preventDefault();
        const orderData = {
            userAddress: `${data.firstName} ${data.lastName} ${data.address} ${data.city} ${data.state} ${data.pin}`,
            phoneNumber: data.phoneNumber,
            email: data.email,
            orderItems: cartItems.map(item => ({
                foodId: item.foodId,
                quantity: quantities[item.id],
                price: item.price * quantities[item.id],
                category: item.id,
                imageUrl: item.imageUrl,
                description: item.description,
                name: item.name
            })),
            amount: total.toFixed(2),
            orderStatus: "Preparing"
        };
        try {
            const response = await axios.post(backendUrl + '/api/orders/create', orderData, { headers: { 'Authorization': `Bearer ${token}` } });
            if (response.status === 201 && response.data.razorpayOrderId) {
                initiateRazorpayPayment(response.data);
            } else {
                toast.error("Unable to place order, Please try again.");
            }
        } catch (error) {
            toast.error("Unable to place order, Please try again.");
        }
    };

    const initiateRazorpayPayment = (order) => {
        const options = {
            key: RazorpayKey,
            amount: order.amount,
            currency: "INR",
            name: "YummiGo",
            description: "YummiGo Food order Payment",
            order_id: order.razorpayOrderId,
            handler: async function (razorpayResponse) {
                await verifyPayment(razorpayResponse);
            },
            prefill: {
                name: `${data.firstName} ${data.lastName}`,
                email: data.email,
                contact: data.phoneNumber,
            },
            theme: { color: '#3399cc' },
            modal: {
                ondismiss: async function () {
                    toast.error("Payment Cancelled.");
                    await deleteOrder(order.id);
                },
            },
        };
        const razorpay = new window.Razorpay(options);
        razorpay.open();
    };

    const verifyPayment = async (razorpayResponse) => {
        const paymentData = {
            razorpay_payment_id: razorpayResponse.razorpay_payment_id,
            razorpay_order_id: razorpayResponse.razorpay_order_id,
            razorpay_signature: razorpayResponse.razorpay_signature
        };
        try {          
            const response = await axios.post(backendUrl + '/api/orders/verify', paymentData, { headers: { 'Authorization': `Bearer ${token}` } });
            if (response.status === 200) {
                toast.success("Payment successfull.");
                await clearCart();
                navigate('/myorders');
            } else {
                toast.error('Payment failed. Please try again1.');
                navigate('/');
            }
        } catch (error) {
            toast.error('Payment failed. Please try again2.');
        }
    };

    const deleteOrder = async (orderId) => {
        try {
            await axios.delete(backendUrl+"/api/orders/"+ orderId, {
                headers: {'Authorization': `Bearer ${token}`},
            });
        } catch (error) {
            toast.error("Something went Wrong. Contact suppot.");
        }
    };

    const clearCart = async () => {
        try {
            await axios.delete(backendUrl+"/api/cart", {
                headers: {'Authorization': `Bearer ${token}`},
            });
            setQuantities({});
        } catch (error) {
            toast.error("Error while clearing the cart.");
        }
    };

    const cartItems = foodList.filter(food => quantities[food.id] > 0);

    const { subtotal, shipping, tax, total } = calculateCartTotals(cartItems, quantities);

    return (
        <div className='container mt-4'>
            <main>
                <div className="py-5 text-center">
                    <img className="d-block mx-auto" src={assets.logo} alt="" width="98" height="98" />
                </div>
                <div className="row g-5">
                    <div className="col-md-5 col-lg-4 order-md-last">
                        <h4 className="d-flex justify-content-between align-items-center mb-3">
                            <span className="text-primary">Your cart</span>
                            <span className="badge bg-primary rounded-pill">{cartItems.length}</span>
                        </h4>
                        <ul className="list-group mb-3">
                            {
                                cartItems.map(item => (
                                    <li className="list-group-item d-flex justify-content-between lh-sm">
                                        <div>
                                            <h6 className="my-0">{item.name}</h6>
                                            <small className="text-body-secondary">Qty: {quantities[item.id]}</small>
                                        </div>
                                        <span className="text-body-secondary">&#8377;{item.price * quantities[item.id]}</span>
                                    </li>
                                ))
                            }
                            <li className="list-group-item d-flex justify-content-between">
                                <div>
                                    <span>Shipping</span>
                                </div>
                                <span className="text-body-secondary">&#8377;{subtotal === 0 ? 0.0 : shipping.toFixed(2)}</span>
                            </li>
                            <li className="list-group-item d-flex justify-content-between lh-sm">
                                <div>
                                    <span>Tax (10%)</span>
                                </div>
                                <span className="text-body-secondary">&#8377;{tax.toFixed(2)}</span>
                            </li>

                            <li className="list-group-item d-flex justify-content-between">
                                <span>Total (INR)</span>
                                <strong>&#8377;{total.toFixed(2)}</strong>
                            </li>
                        </ul>

                    </div>
                    <div className="col-md-7 col-lg-8">
                        <h4 className="mb-3">Billing address</h4>
                        <form className="needs-validation" onSubmit={onSubmitHandler}>
                            <div className="row g-3">
                                <div className="col-sm-6">
                                    <label htmlFor="firstName" className="form-label">First name</label>
                                    <input type="text" className="form-control" id="firstName" placeholder="John" required name='firstName' onChange={onChangeHandler} value={data.firstName} />

                                </div>
                                <div className="col-sm-6">
                                    <label htmlFor="lastName" className="form-label">Last name</label>
                                    <input type="text" className="form-control" id="lastName" placeholder="Doe" value={data.lastName} onChange={onChangeHandler} name='lastName' required />

                                </div>
                                <div className="col-12">
                                    <label htmlFor="email" className="form-label">Email</label>
                                    <div className="input-group has-validation">
                                        <span className="input-group-text">@</span>
                                        <input type="email" className="form-control" id="email" placeholder="Email" required name='email' onChange={onChangeHandler} value={data.email} />
                                    </div>
                                </div>

                                <div className="col-12">
                                    <label htmlFor="phone" className="form-label">Phone Number</label>
                                    <input type="number" className="form-control" id="number" placeholder="9876543210" required name='phoneNumber' onChange={onChangeHandler} value={data.phoneNumber} />
                                </div>

                                <div className="col-12">
                                    <label htmlFor="address" className="form-label">Address</label>
                                    <input type="text" className="form-control" id="address" placeholder="1234 Main St" required name='address' onChange={onChangeHandler} value={data.address} />
                                </div>

                                <div className="col-md-4">
                                    <label htmlFor="state" className="form-label">State</label>
                                    <select className="form-select" id="state" required name='state' onChange={onChangeHandler} value={data.state}>
                                        <option value="">Choose...</option>
                                        <option>Madhya Pradesh</option>
                                    </select>
                                </div>

                                <div className="col-md-5">
                                    <label htmlFor="city" className="form-label">City</label>
                                    <select className="form-select" id="city" required name='city' onChange={onChangeHandler} value={data.city}>
                                        <option value="">Choose...</option>
                                        <option>Indore</option>
                                    </select>

                                </div>

                                <div className="col-md-3">
                                    <label htmlFor="pin" className="form-label">Pin</label>
                                    <input type="number" className="form-control" id="pin" placeholder="405080" required name='pin' onChange={onChangeHandler} value={data.pin} />

                                </div>
                            </div>
                            <hr className="my-4" />
                            <button className="w-100 btn btn-primary btn-lg" type="submit" disabled={cartItems.length === 0}>Continue to checkout</button>
                        </form>
                    </div>
                </div>
            </main>
        </div>
    )
}

export default PlaceOrder