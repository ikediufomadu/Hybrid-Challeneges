import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function Secure() {
  const navigate = useNavigate();
  const [error, setError] = useState("");

  const handleLogout = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:4000/v1/api/logout", {
        method: "PUT",
        credentials: "include", // Send cookies along with the request
      });
      if (response.ok) {
        const data = await response.json(); // Parse JSON response
        if (data.success) {
          // Redirect to the login page after successful logout
          navigate("/");
        } else {
          // Handle failed logout
          console.error("Logout failed:", data.message);
          setError(data.message);
        }
      } else {
        // Handle failed logout
        console.error("Logout failed");
        setError("Logout failed. Please try again later.");
      }
    } catch (error) {
      // Handle logout error
      console.error("Error logging out:", error);
      setError("Error logging out. Please try again later.");
    }
  };

  return (
    <div>
      <h2>Welcome, user!</h2>
      <p>This is a secure page.</p>
      <button onClick={handleLogout}>Logout</button>
    </div>
  );
}

export default Secure;
