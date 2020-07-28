package com.sce.demo.model;

public enum TokenEnum {
   /* 2 üres zseton ("joker"), 0 pont értékben.
            1 pont: A ×6, E ×6, K ×6, T ×5, Á ×4, L ×4, N ×4, R ×4, I ×3, M ×3, O ×3, S ×3
            2 pont: B ×3, D ×3, G ×3, Ó ×3
            3 pont: É ×3, H ×2, SZ ×2, V ×2
            4 pont: F ×2, GY ×2, J ×2, Ö ×2, P ×2, U ×2, Ü ×2, Z ×2
            5 pont: C ×1, Í ×1, NY ×1
            7 pont: CS ×1, Ő ×1, Ú ×1, Ű ×1
            8 pont: LY ×1, ZS ×1
            10 pont: TY ×1*/

    A(1),E(1),K(1),T(1),Á(1),L(1),N(1),R(1),I(1),M(1),O(1),S(1),
    B(2),D(2),G(2),Ó(2),
    É(3),H(3),SZ(3),V(3),
    F(4),GY(4),J(4),Ö(4),P(4),U(4),Ü(4),Z(4),
    C(5),Í(5),NY(5),
    CS(7),Ő(7),Ú(7),Ű(7),
    LY(8),ZS(8),
    TY(10),NULL(0),
    JOKER(0);

    public final int value;
    private TokenEnum(int value){
        this.value = value;
    }
}
