import { SESSION_API_ENDPOINTS } from "@/mapper/sessionMapper";
import axiosInstance from "@/utils/axios";
import { Session } from "@/lib/types"; // Make sure this matches your session object

interface GetSessionsByStatusParams {
  accessToken: string;
  status?: string;
  page?: number;
  size?: number;
  idType?: string;
  id?: number;
}

interface PaginatedSessionResponse {
  content: Session[];
  page: {
    totalElements: number;
    totalPages: number;
  };
}

export const getSessionsByStatus = async ({
  accessToken,
  status = "completed",
  page = 0,
  size = 4,
  idType,
  id,
}: GetSessionsByStatusParams): Promise<PaginatedSessionResponse> => {
  try {
    const url = new URL(SESSION_API_ENDPOINTS.GET_SESSIONS_BY_STATUS);
    url.searchParams.append("status", status);
    url.searchParams.append("page", page.toString());
    url.searchParams.append("size", size.toString());

    if (idType) {
      url.searchParams.append("idType", idType);
    }
    if (id) {
      url.searchParams.append("id", id.toString());
    }

    const response = await axiosInstance.get<PaginatedSessionResponse>(url.toString(), {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });

    return response.data;
  } catch (error) {
    console.error("Error fetching sessions by status:", error);
    throw error; // re-throw to allow catch in caller
  }
};
