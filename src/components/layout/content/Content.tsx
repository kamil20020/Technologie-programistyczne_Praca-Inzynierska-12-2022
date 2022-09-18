﻿import { Grid } from "@mui/material";
import './Content.css';

type LayoutProps = {
    children: React.ReactNode;
};

const Content = ({ children }: LayoutProps) => {
    return (
        <main>
            <Grid container justifyContent="center" alignItems="center">
                {children}
            </Grid>
        </main>
    )
}

export default Content;