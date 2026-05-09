import React, { useState } from "react";
import axios from "axios";
import { toast, ToastContainer } from "react-toastify";
import EmailPasswordForm from "./forms/EmailPasswordForm";
import Card from "./forms/Card";
import { useNavigate } from "react-router-dom";
import "./css/App.css";
import "react-toastify/dist/ReactToastify.css";
import "./css/Button.css";
import "./css/Input.css";
import "./css/Card.css";

const CheckResetPasswordPage = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const token = new URLSearchParams(window.location.search).get("token") || "";

  const showError = (error, message = "Check if the account exists.") => {
    console.error(message, error);
    toast.error(`Error: ${error.response?.data || error.message}. ${message}`);
  };

  const handleResetPassword = async () => {
    if (!email || !password) {
      toast.warning("Please fill in email and password!");
      return;
    }

    try {
      const response = await axios.post("/api/v1/auth/reset-password", {
        token: decodeURIComponent(token).trim(),
        email: email.trim(),
        newPassword: password.trim(),
      });

      if (response.status === 200) {
        toast.success("Password set successfully! You can log in.");
        navigate("/");
      }
    } catch (error) {
      showError(error, "Failed to reset password.");
    }
  };

  return (
    <div>
      <h2>Change Password</h2>
      <Card>
        <EmailPasswordForm
          email={email}
          password={password}
          setEmail={setEmail}
          setPassword={setPassword}
        />
        <button className="pretty-button" onClick={handleResetPassword}>
          Change Password
        </button>
        <button className="pretty-button" onClick={() => navigate("/")}>
          Back
        </button>
      </Card>
      <ToastContainer />
    </div>
  );
};

export default CheckResetPasswordPage;