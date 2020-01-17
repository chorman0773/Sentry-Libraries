
#[cfg(linux)]
mod linux;

#[cfg(windows)]
mod windows;

#[cfg(linux)]
pub use linux::*;

#[cfg(windows)]
pub use windows::*;
