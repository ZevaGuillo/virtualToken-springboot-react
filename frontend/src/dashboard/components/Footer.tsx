import { Github, Linkedin } from "lucide-react"
import React from "react"

export const Footer = () => {
  return (
    <footer className="bg-background border-t p-4">
        <div className="container mx-auto flex justify-between items-center">
          <p className="text-sm text-muted-foreground">© 2024 VirtualToken. All rights reserved.</p>
          <div className="flex space-x-4">
            <a href="https://www.linkedin.com/in/guillermo-david-zevallos-escalante/" target="_blank" rel="noopener noreferrer" className="text-muted-foreground hover:text-primary">
              <Linkedin className="h-5 w-5" />
              <span className="sr-only">LinkedIn</span>
            </a>
            <a href="https://github.com/ZevaGuillo" target="_blank" rel="noopener noreferrer" className="text-muted-foreground hover:text-primary">
              <Github className="h-5 w-5" />
              <span className="sr-only">GitHub</span>
            </a>
          </div>
        </div>
      </footer>
  )
}

  