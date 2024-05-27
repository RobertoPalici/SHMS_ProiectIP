import React, { useState, useEffect } from 'react';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

interface Notification {
  id: number;
  message: string;
}

const Notifications: React.FC = () => {
  const [notifications, setNotifications] = useState<Notification[]>([]);
  const [lastDisplayedId, setLastDisplayedId] = useState<number | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchNotifications = async () => {
      try {
        const response = await fetch('http://localhost:8081/notifications');
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();

        if (Array.isArray(data.notificationList)) {
          const mappedData: Notification[] = data.notificationList.map((item: any, index: number) => ({
            id: index,
            message: item.message
          }));
          setNotifications(mappedData);
        } else {
          throw new Error('Data format is incorrect');
        }
      } catch (error: unknown) {
        if (error instanceof Error) {
          setError(error.message);
        } else {
          setError('An unknown error occurred');
        }
      }
    };

    fetchNotifications();
  }, []);

  useEffect(() => {
    notifications.forEach((notification) => {
      if (lastDisplayedId === null || notification.id > lastDisplayedId) {
        toast.info(`${notification.message}`);
      }
    });

    if (notifications.length > 0) {
      setLastDisplayedId(notifications[notifications.length - 1].id);
    }
  }, [notifications]);

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div>
      <ToastContainer
        position="bottom-right"
        autoClose={3600000}
        hideProgressBar={true}
        newestOnTop={false}
        rtl={false}
        pauseOnFocusLoss
        pauseOnHover
        theme="colored"
      />
    </div>
  );
};

export default Notifications;
