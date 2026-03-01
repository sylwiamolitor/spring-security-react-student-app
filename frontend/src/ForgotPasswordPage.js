import React, { useState } from "react";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import Card from "./forms/Card";
import { useNavigate } from "react-router-dom";
import "./css/App.css";
import "react-toastify/dist/ReactToastify.css";
import "./css/Button.css";
import "./css/Input.css";
import "./css/Card.css";

const ForgotPasswordPage = () => {
  const [email, setEmail] = useState("");
  const navigate = useNavigate();

  const showError = (error, message = "Check if the account exists.") => {
    console.error(message, error);
    toast.error(`Error: ${error.response?.data || error.message}. ${message}`);
  };

  const handleForgotPassword = async () => {
    if (!email) {
      toast.warning("Please fill in email!");
      return;
    }

    try {
      const response = await axios.post("/api/v1/auth/forgot-password", {
        email: email.trim(),
      });

      if (response.status === 200) {
        toast.success("Email sent successfully!");
      }
    } catch (error) {
      showError(error, "Failed to send reset email.");
    }
  };

  return (
    <div>
      <h2>Forgot Password</h2>
      <Card>
        <div>
          <label htmlFor="email">Email:</label>
          <input
            className="input-field"
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="Enter your email"
          />
        </div>
        <button className="pretty-button" onClick={handleForgotPassword}>
          Send Email
        </button>
        <button className="pretty-button" onClick={() => navigate("/")}>
          Back
        </button>
      </Card>
      <ToastContainer />
    </div>
  );
};

export default ForgotPasswordPage;