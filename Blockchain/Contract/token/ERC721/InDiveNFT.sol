// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./ERC721.sol";
import "./extensions/ERC721URIStorage.sol";
import "./extensions/ERC721Enumerable.sol";
import "../../utils/Counters.sol";

contract InDiveNFT is ERC721, ERC721URIStorage, ERC721Enumerable {
    // 토큰 Id를 관리할 객체
    using Counters for Counters.Counter;
    Counters.Counter private _tokenIds;

    address public owner;

    // NFT를 발급하기 위한 최소 후원 값
    mapping(address => uint256) minNFTValue;

    constructor () ERC721("InDiveNFT", "IVEN") {
        owner = msg.sender;
    }

    // NFT 제작
    function safeMint(address _to, string memory _tokenURI) public returns (uint256) {

        // 새로운 토큰 Id 값 증가
        _tokenIds.increment();
        uint256 newItemId = _tokenIds.current();

        // nft 제작
        _mint(_to, newItemId);
        // URI 저장
        _setTokenURI(newItemId, _tokenURI);

        return newItemId;
    }

    // 해당 주소가 소유한 NFT들을 조회한다.
    function getNFTTokens(address _nftTokenOwner) view public returns (string[] memory) {

        uint256 balanceLength = balanceOf(_nftTokenOwner);
        string[] memory nftURIList = new string[](balanceLength);

        for(uint256 i = 0; i < balanceLength; i++){
	        uint256 nftTokenId = tokenOfOwnerByIndex(_nftTokenOwner, i);
	        string memory nftTokenURI = tokenURI(nftTokenId);
            nftURIList[i] = nftTokenURI;
        }
        return nftURIList;
    }

    // tokenId 로 tokenURI 반환
    function tokenURI(uint256 tokenId) public view override(ERC721, ERC721URIStorage) returns (string memory) {
        return super.tokenURI(tokenId);
    }

    // tokenId 에 해당하는 NFT 삭제
    function _burn(uint256 tokenId) internal override(ERC721, ERC721URIStorage) {
        super._burn(tokenId);
    }

    function _beforeConsecutiveTokenTransfer(
        address,
        address,
        uint256,
        uint96 size
    ) internal virtual override(ERC721, ERC721Enumerable) {
        // We revert because enumerability is not supported with consecutive batch minting.
        // This conditional is only needed to silence spurious warnings about unreachable code.
        if (size > 0) {
            revert("ERC721Enumerable: consecutive transfers not supported");
        }
    }

  function _beforeTokenTransfer(address from, address to, uint256 tokenId) internal override(ERC721, ERC721Enumerable) {
    super._beforeTokenTransfer(from, to, tokenId);
  }

  function supportsInterface(bytes4 interfaceId) public view override(ERC721, ERC721Enumerable) returns (bool) {
    return super.supportsInterface(interfaceId);
  }

  function curruntTokenId() public view returns (uint256){
      return _tokenIds.current();
  }
}