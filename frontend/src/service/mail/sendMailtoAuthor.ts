import axiosInstance from "@/utils/axios";

interface SendMailPayload {
  userId: string;
  message: string;
}

export const sendMailToUser = async (payload: SendMailPayload): Promise<void> => {
  try {
    const response = await axiosInstance.post("https://api.example.com/send-mail", payload, {
      headers: {
        "Content-Type": "application/json",
      },
    });
    console.log("Mail sent successfully:", response.data);
  } catch (error) {
    console.error("Error sending mail:", error);
    throw error;
  }
};