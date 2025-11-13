const ErrorDisplay = ({ message, onDismiss }) => {
  useEffect(() => {
    if (!message) return;
    const timer = setTimeout(() => {
      onDismiss?.();
    }, 5000);
  }, [message, onDismiss]);

  if (!message) return null;

  return (
    <div className="error-display">
      <div className="error-content">
        <span className="error-message">{message}</span>
        <span className="error-progress"></span>
      </div>
    </div>
  );
};

export const useError = () => {
  const [errorMessage, setErrorMessage] = useState(null);
  const showError = (message) => {
    setErrorMessage(message);
  };
  const dismissError = () => {
    setErrorMessage(null);
  };

  return {
    ErrorDisplay: () => {
      (<ErrorDisplay message={errorMessage} onDismiss={dismissError} />),
        showError,
        dismissError;
    },
  };
};

export default ErrorDisplay;
