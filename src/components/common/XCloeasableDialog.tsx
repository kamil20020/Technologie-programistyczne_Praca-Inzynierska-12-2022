﻿import { Breakpoint, Button, Dialog, DialogTitle, IconButton } from "@mui/material";
import React, { useEffect } from "react";
import CloseIcon from "@mui/icons-material/Close";

export interface XCloeasableDialogProps {
    title: string,
    buttonTitle?: string,
    form: any,
    open?: boolean,
    size?: Breakpoint,
    buttonSx?: any,
    showButton?: boolean
}

const XCloeasableDialog = (props: XCloeasableDialogProps) => {

    const [open, setOpen] = React.useState<boolean>(props.open ? props.open : false);

    useEffect(() => {
        setOpen(props.open as boolean)
    }, [props.open])

    return (
        <React.Fragment>
            {props.showButton &&
                <Button
                    fullWidth
                    variant="contained"
                    color="secondary"
                    onClick={() => setOpen(true)}
                    sx={props.buttonSx ? props.buttonSx : null}
                >
                    {props.buttonTitle ? props.buttonTitle : props.title}
                </Button>
            }
            <Dialog
                title={props.title}
                maxWidth={props.size !== null ? props.size : "xs"}
                fullWidth={true}
                open={open}
                onClose={() => setOpen(false)}
            >
                <IconButton
                    onClick={() => setOpen(false)}
                    style={{ position: "absolute", right: 0 }}
                >
                    <CloseIcon fontSize="large"/>
                </IconButton>
                <DialogTitle textAlign="center" sx={{mt: 4, mb: 2}}>{props.title}</DialogTitle>
                {props.form}
                <div style={{paddingBottom: 50}}></div>
            </Dialog>
        </React.Fragment>
    );
}

export default XCloeasableDialog;