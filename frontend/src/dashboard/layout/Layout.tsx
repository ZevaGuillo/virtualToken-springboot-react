import React from "react";
import { Footer } from "../components/Footer";
import { Navbar } from "../components/Navbar";

interface LayoutProps {
  children: React.ReactNode;
}
export const Layout: React.FC<LayoutProps> = ({ children }) => {
  return (
    <div className="flex flex-col min-h-screen">
      <Navbar />
      <main className="flex-1 p-4">{children}</main>

      <Footer />
    </div>
  );
};
