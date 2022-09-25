// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.4;

import "./ERC20/InDiveToken.sol";
import "./ERC20/IERC20.sol";

contract Indive {

    // 토큰 컨트랙트
    IERC20 _InDiveTokenContract;

    event donate(address artist, address donator, uint256 value, string message);

    // 후원자 목록.. donatorList[아티스트 주소] = 후원자 리스트
    mapping(address => DonationInfo[]) public donatorList;

    //  ranking[아티스트 주소][사용자 주소] = 후원 수량
    mapping(address => mapping(address => bool)) public isDonated;

    struct DonationInfo{
        address addr;
        uint256 value;
    }

    constructor (address _InDiveTokenContractAddress) {
        _InDiveTokenContract = IERC20(_InDiveTokenContractAddress);
    }

    // approve : 컨트랙트 호출자가 to 에게 인출할 권리를 부여한다.
    function approve(address _to, uint256 _value, string memory _message) public returns (bool){
        require(_to != address(0));
        require(_value > 0);

        _InDiveTokenContract.approve(_to, _value);

        // 배열에 후원 값을 저장한다.
        DonationInfo[] memory list = donatorList[_to];

        for(uint i ; i < list.length ; i++){
            if(list[i].addr == msg.sender){
                list[i].value += _value;
            }
        }

        // 후원 내용을 블록에 기록한다.
        emit donate(_to, msg.sender, _value, _message);

        return true;
    }

    // donate : 컨트랙트 호출자(to)가 from 에게서 토큰을 출금한다.
    function transferfrom(address _from, address _to, uint256 _value) public returns (bool) {
        
        _InDiveTokenContract.transferFrom(_from, _to, _value);

        return true;
    }

}