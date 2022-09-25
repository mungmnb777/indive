// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./ERC20.sol";

contract InDiveToken is ERC20 {
    uint public INITIAL_SUPPLY = 1000000;

    constructor() public ERC20("InDive Token", "IVE"){
        _mint(msg.sender, INITIAL_SUPPLY);
    }
    
}