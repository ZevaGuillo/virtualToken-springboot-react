import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { useAuthStore } from "@/store/useAuthStore";
import { LogOut } from "lucide-react";

export const Navbar = () => {
    const logoutUser = useAuthStore((state) => state.logoutUser);
    const user = useAuthStore((state) => state.user);


    const initials = user?.username ? user?.username
    .split(' ')
    .map(name => name[0])
    .join('')
    .toUpperCase() : ""


  return (
    <header className="flex items-center justify-between p-4 bg-background border-b">
      <div className="flex items-center space-x-4">
        <h1 className="text-2xl font-bold text-primary">Virtual Token</h1>
      </div>
      <div className="flex items-center space-x-4">
        <Avatar className="h-8 w-8">
          <AvatarFallback>{initials}</AvatarFallback>
        </Avatar>
        <Button variant="outline" size="sm" onClick={logoutUser}>
          <LogOut className="mr-2 h-4 w-4" />
          Logout
        </Button>
      </div>
    </header>
  );
};
