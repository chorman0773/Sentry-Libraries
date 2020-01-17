#![cfg(unix)]

extern crate nix;

use std::path::Path;
use self::nix::unistd::Uid;

fn install_as_root(){
    let dir = "/usr/lib/sentry";
}

pub fn install(){
    if nix::unistd::geteuid() == Uid(0){
        // We are root, do root stuff
        return install_as_root()
    }else{

    }
}
