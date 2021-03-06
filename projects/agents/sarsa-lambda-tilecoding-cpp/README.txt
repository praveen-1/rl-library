======================================
Sarsa-Lambda agent with Tile-Coding
======================================

This project contains an agent for the 3D Mountain Car Task.
It implements the Sarsa(lambda) algorithm with tile-coding.

It is based on the agent for the 2D Mountain Car created
by Adam White.

=================================
Building instructions
=================================

It is required to have installed the RL-Glue library (version 3.0).

To compile the environment just use the g++ compiler (or any ANSI C++
compiler) giving the following command:

$> make

If your installation of RL-Glue or the RL-Glue C/C++ codec is not in
your header and library search path, you may need to edit the Makefile
with LDFLAGS and CFLAGS pointing to your headers and libraries.  There are instructions on how
to do this inside the Makefile.

================================
Contact Info
================================

Ioannis Partalas

Department of Informatics
Aristotle University
Thessaloniki, 54124, Greece

email: partalas@csd.auth.gr
home page: http://users.auth.gr/~partalas
