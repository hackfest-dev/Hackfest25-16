// pages/session-category.tsx

import { useEffect, useState } from "react";
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, CartesianGrid } from "recharts";
import { fetchSessionCategoryCount, SessionCategoryCount } from '@/service/session/getSessionCategoryCount'
import { useSelector } from "react-redux";
import { RootState } from "@/store";

const transformDataForChart = (data: SessionCategoryCount) => {
  return Object.entries(data).map(([key, value]) => ({
    category: key.replace(/Count$/, "").replace(/([A-Z])/g, " $1").trim(),
    count: value,
  }));
};

const SessionCategoryChartPage = () => {
  const [chartData, setChartData] = useState<{ category: string; count: number }[]>([]);
  const [loading, setLoading] = useState(true);
  const accessToken = useSelector((state: RootState) => state.auth.accessToken);

  useEffect(() => {
    const loadData = async () => {
      try {
        const rawData = await fetchSessionCategoryCount(accessToken);
        const formattedData = transformDataForChart(rawData);
        setChartData(formattedData as any[] );
      } catch (error) {
        console.error("Failed to load session category data", error);
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, []);

  return (
    <div className="p-8 min-h-screen bg-gray-50">
      <h1 className="text-3xl font-bold mb-6 text-green-700 text-center">Session Categories Histogram</h1>

      {loading ? (
        <p className="text-center text-gray-600">Loading...</p>
      ) : (
        <ResponsiveContainer width="100%" height={400}>
          <BarChart data={chartData} margin={{ top: 10, right: 30, left: 20, bottom: 60 }}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="category" angle={-30} textAnchor="end" interval={0} height={80} />
            <YAxis />
            <Tooltip />
            <Bar dataKey="count" fill="#34D399" />
          </BarChart>
        </ResponsiveContainer>
      )}
    </div>
  );
};

export default SessionCategoryChartPage;
