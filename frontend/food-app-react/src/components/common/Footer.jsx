const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer-content">
        <p>&copy; {new Date().getFullYear()} Bintang Food App</p>
        <div className="footer-links">
          <a href="#" className="footer-link">
            Term of Service
          </a>
          <a href="#" className="footer-link">
            Privacy Policy
          </a>
          <a href="#" className="footer-link">
            Contact Us
          </a>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
