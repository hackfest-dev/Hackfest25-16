import axiosInstance from "@/utils/axios";
import { EMAIL_API_ENDPOINTS } from "@/mapper/emailMapper";

export const sendMailToAuthor = async (
  emailData: {
    subject: string;
    body: string;
  },
  userId: number,
  accessToken: string
) => {
  try {
    const formData = new FormData();
    formData.append("subject", emailData.subject);
    formData.append("body", emailData.body);
    const response = await axiosInstance.post(
      EMAIL_API_ENDPOINTS.SEND_EMAIL(userId),
      formData,
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
      }
    );

    return response;
  } catch (error: any) {
    if (error.response) {
      console.error("Error response data:", error.response.data);
    } else if (error.request) {
      console.error("Error request:", error.request);
    } else {
      console.error("Error message:", error.message);
    }
    throw error;
  }
};
